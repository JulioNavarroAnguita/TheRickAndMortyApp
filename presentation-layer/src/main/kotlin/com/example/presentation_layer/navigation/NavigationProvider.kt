@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.example.presentation_layer.navigation

import CharacterDetailScreenView
import EpisodeDetailScreenView
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.presentation_layer.feature.MyBottomBar
import com.example.presentation_layer.feature.chatacter.detail.viewmodel.CharacterDetailViewModel
import com.example.presentation_layer.feature.chatacter.list.ui.CharacterListScreenView
import com.example.presentation_layer.feature.chatacter.list.viewmodel.CharacterListViewModel
import com.example.presentation_layer.feature.episode.detail.viewmodel.EpisodeDetailViewModel
import com.example.presentation_layer.feature.episode.list.ui.EpisodeListScreenView
import com.example.presentation_layer.feature.episode.list.viewmodel.EpisodeListViewModel
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RickAndMortyNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = {
            MyBottomBar(onNavigationItemClick = { navigationScreen ->
                navController.navigate(navigationScreen)
            })
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = CharactersHome,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<CharactersHome> {
                val characterListViewModel: CharacterListViewModel = hiltViewModel()
                CharactersHomeScreen(
                    viewModel { characterListViewModel },
                    navigateToCharacterDetail = { id ->
                        navController.navigate(CharacterDetail(itemId = id))
                    }
                )
            }
            composable<EpisodesHome> {
                val episodeListViewModel: EpisodeListViewModel = hiltViewModel()
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
                EpisodeDetailScreen(
                    viewModel { episodeDetailViewModel },
                    itemId = episodeId

                )
            }
            composable<CharacterDetail> { navBackStackEntry ->
                val character = requireNotNull(navBackStackEntry.toRoute<CharacterDetail>())
                val characterDetailViewModel: CharacterDetailViewModel = hiltViewModel()
                CharacterDetailScreen(
                    viewModel { characterDetailViewModel },
                    itemId = character.itemId,
                    onBackPressed = {
                        navController.navigate(CharactersHome)
                    },
                    onEpisodeClick = { episodeId ->
                        navController.navigate(EpisodeDetail(episodeId))
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
    onBackPressed: () -> Unit,
    onEpisodeClick: (Int) -> Unit
) {
    viewModel.fetchCharacterDetail(itemId)
    val state by viewModel.state.collectAsState()
    CharacterDetailScreenView(
        onBackPressedAction = {
            onBackPressed()
        },
        characterDetailState = state,
        onEpisodeClickAction = { episodeId ->
//                onEpisodeClick(episodeId) manejar bottomsheet
        },
        onRefreshClickAction = {
            viewModel.fetchCharacterDetail(itemId)
        }
    )
}