package com.example.presentation_layer.navigation

import CharacterDetailScreenView
import EpisodeDetailScreenView
import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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
import com.example.presentation_layer.navigation.NavigationScreen.CharacterDetail
import com.example.presentation_layer.navigation.NavigationScreen.CharactersHome
import com.example.presentation_layer.navigation.NavigationScreen.EpisodeDetail
import com.example.presentation_layer.navigation.NavigationScreen.EpisodesHome
import com.example.presentation_layer.navigation.NavigationScreen.LocationsHome
import com.example.presentation_layer.ui.theme.TheRickAndMortyAppTheme
import kotlinx.serialization.Serializable

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun RickAndMortyApp() {
    val systemDarkTheme = isSystemInDarkTheme()

    var darkTheme by remember { mutableStateOf(systemDarkTheme) }
    TheRickAndMortyAppTheme(darkTheme = darkTheme) {
        val navController = rememberNavController()
        val selectedTabIndex = remember { mutableIntStateOf(0) }
        val title = remember { mutableStateOf(AnnotatedString("")) }
        val showBackButton = remember { mutableStateOf(false) }

        var showBottomBar by remember { mutableStateOf(true) }
        val animatedOffset = animateDpAsState(
            targetValue = if (showBottomBar) 0.dp else 56.dp,
            animationSpec = tween(durationMillis = 300), label = ""
        )
        Scaffold(
            topBar = {
                AppBar(
                    darkTheme = darkTheme,
                    showBackButton = showBackButton.value,
                    title = title.value,
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    onDarkModeClicked = {
                        darkTheme = !darkTheme
                    }
                )
            },
            bottomBar = {
                if (showBottomBar) {
                    Box(modifier = Modifier.offset(y = animatedOffset.value)) {
                        MyBottomBar(
                            selectedTabIndex = selectedTabIndex,
                            onNavigationItemClick = { navigationScreen ->
                                navController.navigate(navigationScreen) {
                                    popUpTo<CharactersHome> {
                                        inclusive = navigationScreen is CharactersHome
                                    }
                                }
                            })
                    }
                }
            },
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = CharactersHome,
                modifier = Modifier.padding(paddingValues)
            ) {
                // Character
                composable<CharactersHome> {
                    val characterListViewModel: CharacterListViewModel = hiltViewModel()
                    val charactersTitle = stringResource(id = R.string.characters)
                    LaunchedEffect(Unit) {
                        showBottomBar = true
                        selectedTabIndex.intValue = 0
                        showBackButton.value = false
                        title.value = AnnotatedString(charactersTitle)
                    }
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
                    val characterDetailTitle = stringResource(id = R.string.character_detail)
                    LaunchedEffect(Unit) {
                        showBottomBar = false
                        showBackButton.value = true
                        title.value = AnnotatedString(characterDetailTitle)
                    }
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
                    val episodesTitle = stringResource(id = R.string.episodes)
                    LaunchedEffect(Unit) {
                        showBottomBar = true
                        showBackButton.value = false
                        selectedTabIndex.intValue = 1
                        title.value = AnnotatedString(episodesTitle)
                    }
                    EpisodesHomeScreen(
                        viewModel { episodeListViewModel },
                        navigateToEpisodeDetail = { episodeId ->
                            navController.navigate(EpisodeDetail(episodeId = episodeId))
                        }
                    )
                }
                composable<EpisodeDetail> { navBackStackEntry ->
                    val episodeId =
                        requireNotNull(navBackStackEntry.toRoute<EpisodeDetail>()).episodeId
                    val episodeDetailViewModel: EpisodeDetailViewModel = hiltViewModel()
                    val episodeDetailTitle = stringResource(id = R.string.episode_detail)
                    LaunchedEffect(Unit) {
                        showBottomBar = false
                        showBackButton.value = true
                        title.value = AnnotatedString(episodeDetailTitle)
                    }
                    EpisodeDetailScreen(
                        viewModel { episodeDetailViewModel },
                        itemId = episodeId
                    )
                }
                // Location
                composable<LocationsHome> {
                    val locationListViewModel: LocationListViewModel = hiltViewModel()
                    val locationsTitle = stringResource(id = R.string.locations)
                    LaunchedEffect(Unit) {
                        showBottomBar = true
                        showBackButton.value = false
                        selectedTabIndex.intValue = 2
                        title.value = AnnotatedString(locationsTitle)
                    }
                    LocationsHomeScreen(
                        viewModel { locationListViewModel }
                    )
                }
            }
        }
    }
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
    viewModel: LocationListViewModel
) {
    val state by viewModel.state.collectAsState()
    LocationListScreenView(
        state = state,
        onRefreshAction = {
//            viewModel.fetchCharacterList()
        }
    )
}

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
        },
        onSearchClicked = { inputSearch ->
            viewModel.onSearchClicked(inputSearch)
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