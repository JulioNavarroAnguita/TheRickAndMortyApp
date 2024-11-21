package com.example.presentation_layer.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation_layer.navigation.NavigationScreen

@Composable
fun MyBottomBar(
    selectedTabIndex: MutableState<Int>,
    onNavigationItemClick: (NavigationScreen) -> Unit
) {
    val items = listOf(
        BottomNavigationItem(
            label = stringResource(R.string.characters),
            icon = R.drawable.rick_and_morty,
            route = NavigationScreen.CharactersHome
        ),
        BottomNavigationItem(
            label = stringResource(R.string.episodes),
            icon = R.drawable.eoisodes_rick_and_morty_tv,
            route = NavigationScreen.EpisodesHome
        ),
        BottomNavigationItem(
            label = stringResource(R.string.locations),
            icon = R.drawable.icon_planet_rick_and_morty,
            route = NavigationScreen.LocationsHome
        )
    )
    Column {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
                .height(1.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            items.forEachIndexed { index, navigationItem ->
                NavigationBarItem(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        if (selectedTabIndex.value != index) {
                            onNavigationItemClick(navigationItem.route)
                        }
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(if (selectedTabIndex.value == index) 32.dp else 24.dp),
                            painter = painterResource(id = navigationItem.icon),
                            contentDescription = "icon${navigationItem.label}",
                            tint = Color.Unspecified
                        )
                    },
                    label = {
                        Text(text = navigationItem.label, fontSize =  if (selectedTabIndex.value == index) 16.sp else TextUnit.Unspecified)
                    })
            }
        }
    }
}

data class BottomNavigationItem(
    val label: String = "",
    @DrawableRes val icon: Int,
    val route: NavigationScreen = NavigationScreen.CharactersHome
)

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    MyBottomBar(
        selectedTabIndex = mutableIntStateOf(0),
        onNavigationItemClick = {}
    )
}
