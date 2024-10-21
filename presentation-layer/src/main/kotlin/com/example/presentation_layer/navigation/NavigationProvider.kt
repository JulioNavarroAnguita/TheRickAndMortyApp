@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.example.presentation_layer.navigation

import CharacterDetailScreenView
import EpisodeDetailScreenView
import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.presentation.R
import com.example.presentation_layer.components.AppBar
import com.example.presentation_layer.components.MyBottomBar
import com.example.presentation_layer.feature.chatacter.detail.viewmodel.CharacterDetailViewModel
import com.example.presentation_layer.feature.chatacter.list.ui.CharacterListScreenView
import com.example.presentation_layer.feature.chatacter.list.viewmodel.CharacterListViewModel
import com.example.presentation_layer.feature.episode.detail.viewmodel.EpisodeDetailViewModel
import com.example.presentation_layer.feature.episode.list.ui.EpisodeListScreenView
import com.example.presentation_layer.feature.episode.list.viewmodel.EpisodeListViewModel
import com.example.presentation_layer.feature.location.list.ui.LocationListScreenView
import com.example.presentation_layer.feature.location.list.viewmodel.LocationListViewModel
import com.example.presentation_layer.navigation.NavigationScreen.*
import kotlinx.serialization.Serializable

@Composable
fun RickAndMortyApp() {
    RickAndMortyNavGraph()
}

sealed interface NavigationScreen {
    @Serializable
    data object CharactersHome : NavigationScreen

    @Serializable
    data object EpisodesHome : NavigationScreen

    @Serializable
    data object LocationsHome : NavigationScreen

    @Serializable
    data class CharacterDetail(val itemId: Int) : NavigationScreen

    @Serializable
    data class EpisodeDetail(val episodeId: Int) : NavigationScreen
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickAndMortyNavGraph() {
    val modalBottomSheetState = rememberModalBottomSheetState(false)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    var selectedTabIndex = remember { mutableStateOf(0) }
    val titleFromResource = stringResource(id = R.string.characters)
    var title = remember { mutableStateOf(titleFromResource) }
    var showBackButton = remember { mutableStateOf(false) }

    var showBottomBar by remember { mutableStateOf(true) }
    val animatedOffset = animateDpAsState(
        targetValue = if (showBottomBar) 0.dp else 56.dp,
        animationSpec = tween(durationMillis = 300), label = ""
    )
    Scaffold(
        topBar = {
            AppBar(
                showBackButton = showBackButton.value,
                title = title
            ) {
                navController.popBackStack()
            }
        },
        bottomBar = {
            if (showBottomBar) {
                Box(modifier = Modifier.offset(y = animatedOffset.value)) {
                    MyBottomBar(
                        selectedTabIndex = selectedTabIndex,
                        onNavigationItemClick = { navigationScreen -> // arreglar navegacion
                            navController.navigate(navigationScreen) {
                                if (navigationScreen is CharactersHome) popUpTo<CharactersHome> {
                                    inclusive = true
                                } else popUpTo<CharactersHome> { inclusive = false }
                            }
                        })
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = CharactersHome,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Character
            composable<CharactersHome> {
                val characterListViewModel: CharacterListViewModel = hiltViewModel()
                showBottomBar = true
                selectedTabIndex.value = 0
                showBackButton.value = false
                title.value = stringResource(id = R.string.characters)
                CharactersHomeScreen(
                    viewModel { characterListViewModel },
                    navigateToCharacterDetail = { id ->
                        navController.navigate(CharacterDetail(itemId = id))
                    }
                )
            }
            composable<CharacterDetail> { navBackStackEntry ->
                val character = requireNotNull(navBackStackEntry.toRoute<CharacterDetail>())
                val characterDetailViewModel: CharacterDetailViewModel = hiltViewModel()
                showBottomBar = false
                showBackButton.value = true
                title.value = stringResource(id = R.string.character_detail)
                CharacterDetailScreen(
                    viewModel { characterDetailViewModel },
                    itemId = character.itemId,
                    onEpisodeClick = { episodeId ->

                    }
                )
            }

            // Epsiode
            composable<EpisodesHome> {
                val episodeListViewModel: EpisodeListViewModel = hiltViewModel()
                showBottomBar = true
                showBackButton.value = false
                selectedTabIndex.value = 1
                title.value = stringResource(id = R.string.episodes)
                EpisodesHomeScreen(
                    viewModel { episodeListViewModel },
                    navigateToEpisodeDetail = { episodeId ->
                        navController.navigate(EpisodeDetail(episodeId = episodeId))
                    }
                )
            }
            composable<EpisodeDetail> { navBackStackEntry ->
                val episodeId = requireNotNull(navBackStackEntry.toRoute<EpisodeDetail>()).episodeId
                val episodeDetailViewModel: EpisodeDetailViewModel = hiltViewModel()
                showBottomBar = false
                showBackButton.value = true
                title.value = stringResource(id = R.string.episode_detail)
                EpisodeDetailScreen(
                    viewModel { episodeDetailViewModel },
                    itemId = episodeId
                )
            }
            // Location
            composable<LocationsHome> {
                val locationListViewModel: LocationListViewModel = hiltViewModel()
                showBottomBar = true
                showBackButton.value = false
                selectedTabIndex.value = 2
                title.value = stringResource(id = R.string.locations)
                LocationsHomeScreen(
                    viewModel { locationListViewModel },
                    navigateToLocationDetail = { locationId ->
//                        navController.navigate(EpisodeDetail(episodeId = episodeId))
                    }
                )
            }
        }
    }
}


@Composable
fun EpisodesHomeScreen(
    viewModel: EpisodeListViewModel,
    navigateToEpisodeDetail: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    EpisodeListScreenView(
        state = state,
        onClickItem = { itemId ->
            navigateToEpisodeDetail(itemId)
        },
        onRefreshAction = {
//            viewModel.fetchCharacterList()
        }
    )
}

@Composable
fun LocationsHomeScreen(
    viewModel: LocationListViewModel,
    navigateToLocationDetail: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    LocationListScreenView(
        state = state,
        onClickItem = { itemId ->
//            navigateToEpisodeDetail(itemId)
        },
        onRefreshAction = {
//            viewModel.fetchCharacterList()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(
    viewModel: EpisodeDetailViewModel,
    itemId: Int
) {
    viewModel.fetchEpisodeDetail(itemId)
    val state by viewModel.state.collectAsState()
    EpisodeDetailScreenView(
        episodeDetailState = state
    )
}

@Composable
fun CharactersHomeScreen(
    viewModel: CharacterListViewModel,
    navigateToCharacterDetail: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    CharacterListScreenView(
        state = state,
        onClickAction = { characterStatus ->
            viewModel.onChipFilterAction(
                characterStatus = characterStatus
            )
        },
        onClickItem = { itemId ->
            navigateToCharacterDetail(itemId)
        },
        onRefreshAction = {
            viewModel.fetchCharacterList()
        }
    )
}

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    itemId: Int,
    onEpisodeClick: (Int) -> Unit
) {
    viewModel.fetchCharacterDetail(itemId)
    val state by viewModel.state.collectAsState()
    CharacterDetailScreenView(
        onRefreshAction = {
            viewModel.fetchCharacterDetail(itemId)
        },
        characterDetailState = state,
        onEpisodeClickAction = { episodeId ->
            onEpisodeClick(episodeId)
        }
    )
}