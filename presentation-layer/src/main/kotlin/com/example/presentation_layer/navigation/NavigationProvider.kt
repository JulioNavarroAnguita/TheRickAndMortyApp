package com.example.presentation_layer.navigation

import DetailScreenView
import androidx.compose.material.ModalBottomSheetLayout
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
    data class EpisodeDetail(val episodeId: Int) : Navigation
}


@Composable
fun RickAndMortyNavGraph(
    navController: NavHostController = rememberNavController(),
) {
//    val bottomSheetNavigator = rememberBottomSheetNavigator()
//    val navController = rememberNavController(bottomSheetNavigator)
//    ModalBottomSheetLayout(bottomSheetNavigator) {
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


           /* bottomSheet<EpisodeDetail> { navBackStackEntry ->
                val episodeId = requireNotNull(navBackStackEntry.toRoute<EpisodeDetail>().episodeId)
                val characterDetailViewModel: CharacterDetailViewModel = hiltViewModel()
                EpisodeDetailScreen(
                    viewModel { characterDetailViewModel },
                    episodeId = episodeId
                )
            }*/
//        }
    }
}

@Composable
fun HomeScreen(
    viewModel: CharacterListViewModel,
    navigateToCharacterDetail: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    state.characterData?.characterList?.let { characterList ->
        ListScreen(
            characterList = characterList,
            onClickAction = { characterStatus ->
                viewModel.onChipFilterAction(
                    characterStatus = characterStatus
                )
            },
            onClickItem = { itemId ->
                navigateToCharacterDetail(itemId)
            }
        )
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(
    viewModel: CharacterDetailViewModel,
    episodeId: Int
) {
    viewModel.fetchEpisodeDetail(episodeId)
    val state by viewModel.state.collectAsState()
    state.episodeData?.let { episodeDataModel ->
        ModalBottomSheet(onDismissRequest = {}) {
            Text(text = episodeDataModel.episode!!.name)
        }
    }
}
*/

@Composable
fun DetailScreen(
    viewModel: CharacterDetailViewModel,
    itemId: Int,
    onBackPressed: () -> Unit,
    onEpisodeClick: (Int) -> Unit,
) {
    viewModel.fetchCharacterDetail(itemId)
    val state by viewModel.state.collectAsState()
    state.characterData?.let { characterDataModel ->
        DetailScreenView(
            onBackPressed = {
                onBackPressed()
            },
            characterDetailDataModel = characterDataModel,
            onEpisodeClick = { episodeId ->
                onEpisodeClick(episodeId)
            }
        )
    }
}