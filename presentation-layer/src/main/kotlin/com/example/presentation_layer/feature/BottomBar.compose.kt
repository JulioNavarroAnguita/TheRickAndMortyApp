package com.example.presentation_layer.feature

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation_layer.navigation.NavigationScreen


@Composable
fun MyBottomBar(
    onNavigationItemClick: (NavigationScreen) -> Unit
) {
    val items = listOf(
        BottomNavigationItem(
            label = "Characters",
            icon = Icons.Filled.Home,
            route = NavigationScreen.CharactersHome
        ),
        BottomNavigationItem(
            label = "Episodes",
            icon = Icons.Filled.LiveTv,
            route = NavigationScreen.EpisodesHome
        ),
        BottomNavigationItem(
            label = "Locations",
            icon = Icons.Filled.PinDrop,
            route = NavigationScreen.CharactersHome
        )
    )
    NavigationBar {
        items.map { navigationItem ->
            NavigationBarItem(
                selected = false,
                onClick = { onNavigationItemClick(navigationItem.route) },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = "icon${navigationItem.label}"
                    )
                },
                label = {
                    Text(text = navigationItem.label)
                })
        }
    }
}

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: NavigationScreen = NavigationScreen.CharactersHome
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBottomBar(
    onCharactersClickAction: () -> Unit
) {
    Scaffold(
        bottomBar = {
            MyBottomBar {}
        }
    ) { innerPadding ->
        innerPadding
    }
}
