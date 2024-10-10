package com.example.presentation_layer.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import com.example.presentation_layer.feature.chatacter.detail.viewmodel.CharacterDetailViewModel
import com.example.presentation_layer.feature.chatacter.list.ui.ListScreen
import com.example.presentation_layer.feature.chatacter.list.viewmodel.CharacterListViewModel
import com.example.presentation_layer.navigation.Navigations.*
import com.example.presentation_layer.ui.theme.TheRickAndMortyAppTheme
import kotlinx.serialization.Serializable

@Composable
fun RickAndMortyApp() {
    TheRickAndMortyAppTheme {
        val navController = rememberNavController()
        RickAndMortyNavGraph(
            navController = navController
        )

    }
}
sealed class Navigations {
    @Serializable
    object Home
    @Serializable
    data class Detail(val itemId: Int)
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
                navigateToDetail = { id ->
                    navController.navigate(Detail(itemId = id))
                },
                onBackPressed = {
                    navController.navigate(Home)
                }
            )
        }
        composable<Detail> { navBackStackEntry ->
            val character = requireNotNull(navBackStackEntry.toRoute<Detail>())
            val characterDetailViewModel: CharacterDetailViewModel = hiltViewModel()
            DetailScreen(
                viewModel { characterDetailViewModel },
                itemId = character.itemId
            )

        }
    }
}

@Composable
fun HomeScreen(viewModel: CharacterListViewModel, navigateToDetail: (Int) -> Unit, onBackPressed: () -> Unit) {
    val state by viewModel.state.collectAsState()
    state.characterData?.characterList?.let { characterList ->
        ListScreen(
            characterList = characterList,
            onClickAction = { stateMap ->
                viewModel.onChipFilterAction(
                    characterStatus = stateMap
                )
            },
            onClickItem = { itemId ->
                navigateToDetail(itemId)
            },
            onBackPressed = onBackPressed
        )
    }
}

@Composable
fun DetailScreen(
    viewModel: CharacterDetailViewModel,
    itemId: Int
) {
    viewModel.fetchCharacterDetail(itemId)
    val state by viewModel.state.collectAsState()
    state.characterData?.character?.let { character ->
        Column {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
    }
}