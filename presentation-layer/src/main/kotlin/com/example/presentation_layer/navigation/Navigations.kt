package com.example.presentation_layer.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.presentation_layer.feature.chatacter.detail.viewmodel.CharacterDetailViewModel
import com.example.presentation_layer.feature.chatacter.list.ui.ListScreen
import com.example.presentation_layer.feature.chatacter.list.viewmodel.CharacterListViewModel
import com.example.presentation_layer.ui.theme.TheRickAndMortyAppTheme

@Composable
fun RickAndMortyApp() {
    TheRickAndMortyAppTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            NavigationActions(navController)
        }
        RickAndMortyNavGraph(
            navController = navController,
            navigateToHome = navigationActions.navigateToHome,
            navigateToDetail = navigationActions.navigateToDetail
        )

    }
}

sealed class NavScreen(val route: String) {
    object Home : NavScreen(route = "homeScreen")
    object Detail : NavScreen(route = "detailScreen/{itemId}") {
        fun passId(id: Int) = "detailScreen/$id"
    }

}

class NavigationActions(navController: NavController) {
    val navigateToHome = { navController.navigate(NavScreen.Home.route) }

    val navigateToDetail = { id: Int ->
        navController.navigate(NavScreen.Detail.passId(id = id))
    }
}

@Composable
fun RickAndMortyNavGraph(
    navigateToHome: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavScreen.Home.route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NavScreen.Home.route) {
            val characterListViewModel: CharacterListViewModel = hiltViewModel()
            HomeScreen(
                viewModel { characterListViewModel },
                navigateToDetail = navigateToDetail,
            )
        }
        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val itemId = requireNotNull(navBackStackEntry.arguments?.getInt("itemId"))
            val characterDetailViewModel: CharacterDetailViewModel = hiltViewModel()
                DetailScreen(
                    viewModel { characterDetailViewModel },
                    itemId = itemId
                )

        }
    }
}

@Composable
fun HomeScreen(viewModel: CharacterListViewModel, navigateToDetail: (Int) -> Unit) {
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
            })
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