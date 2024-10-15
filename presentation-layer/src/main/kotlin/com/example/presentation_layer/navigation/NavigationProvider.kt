@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.example.presentation_layer.navigation

import DetailScreenView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.presentation_layer.feature.chatacter.detail.viewmodel.CharacterDetailViewModel
import com.example.presentation_layer.feature.chatacter.list.ui.ListScreen
import com.example.presentation_layer.feature.chatacter.list.viewmodel.CharacterListViewModel
import com.example.presentation_layer.navigation.Navigation.CharacterDetail
import com.example.presentation_layer.navigation.Navigation.EpisodeDetail
import com.example.presentation_layer.navigation.Navigation.Home
import com.example.presentation_layer.ui.theme.TheRickAndMortyAppTheme
import kotlinx.serialization.Serializable

@Composable
fun RickAndMortyApp() {
    TheRickAndMortyAppTheme {
        RickAndMortyNavGraph()
    }
}

sealed interface Navigation {
    @Serializable
    data object Home : Navigation

    @Serializable
    data class CharacterDetail(val itemId: Int) : Navigation

    @Serializable
    data class EpisodeDetail(val episodeId: Int? = null) : Navigation
}

@Composable
fun RickAndMortyNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            val characterListViewModel: CharacterListViewModel = hiltViewModel()
            HomeScreen(
                viewModel { characterListViewModel },
                navigateToCharacterDetail = { id ->
                    navController.navigate(CharacterDetail(itemId = id))
                }
            )
        }
        composable<CharacterDetail> { navBackStackEntry ->
            val character = requireNotNull(navBackStackEntry.toRoute<CharacterDetail>())
            val characterDetailViewModel: CharacterDetailViewModel = hiltViewModel()
            DetailScreen(
                viewModel { characterDetailViewModel },
                itemId = character.itemId,
                onBackPressed = {
                    navController.navigate(Home)
                },
                onEpisodeClick = { episodeId ->
                    navController.navigate(EpisodeDetail(episodeId))
                }
            )
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: CharacterListViewModel,
    navigateToCharacterDetail: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    ListScreen(
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
fun DetailScreen(
    viewModel: CharacterDetailViewModel,
    itemId: Int,
    onBackPressed: () -> Unit,
    onEpisodeClick: (Int) -> Unit
) {
    viewModel.fetchCharacterDetail(itemId)
    val state by viewModel.state.collectAsState()
    DetailScreenView(
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