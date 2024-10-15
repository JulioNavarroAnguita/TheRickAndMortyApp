package com.example.presentation_layer.feature

import androidx.compose.material.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController) {
    /*val items = listOf(
//        BottomNavItem("home", "Inicio", Icons.Filled.Home),
//        BottomNavItem("favorites", "Favoritos", Icons.Filled.Favorite),
//        BottomNavItem("profile", "Perfil", Icons.Filled.Person)
    )
*/
    BottomNavigation {
        val currentDestination by navController.currentBackStackEntryAsState()
        val currentRoute = currentDestination?.destination?.route

        /*items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }*/
    }
}

//data class BottomNavItem(val route: NavigationScreen, val title: String, val icon: ImageVector)

