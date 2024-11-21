package com.example.presentation_layer.feature.location.list.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain_layer.model.LocationResultBo.LocationBo
import com.example.presentation.R
import com.example.presentation_layer.components.EmptyScreen
import com.example.presentation_layer.components.ErrorScreen
import com.example.presentation_layer.feature.location.list.viewmodel.LocationListState
import com.example.presentation_layer.ui.theme.purple
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun LocationListScreenView(
    state: LocationListState,
    onRefreshAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when (state) {
            is LocationListState.Data -> LocationListDataScreen(
                locationList = state.locations
            )

            is LocationListState.Error -> ErrorScreen(
                onRefreshClick = onRefreshAction,
                image = R.drawable.error,
                message = R.string.error_message
            )

            LocationListState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun LocationListDataScreen(
    locationList: List<LocationBo>
) {
    if (locationList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            LocationList(locationList = locationList)
            Spacer(modifier = Modifier.height(8.dp))
        }
    } else EmptyScreen(image = R.drawable.empty_screen, message = R.string.empty_list)

}

@Composable
fun LocationList(locationList: List<LocationBo>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(locationList) { location ->
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .border(
                        BorderStroke(2.dp, Color.Black), RoundedCornerShape(8.dp)
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = location.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisSpacing = 4.dp, // Espacio horizontal entre elementos
                    ) {
                        Text(
                            text = "Type:",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = location.type,
                            style = MaterialTheme.typography.bodyMedium,
                            color = purple
                        )
                    }
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisSpacing = 4.dp
                    ) {
                        Text(
                            text = stringResource(R.string.dimension),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = location.dimension,
                            style = MaterialTheme.typography.bodyMedium,
                            color = purple
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LocationListPreview() {
    LocationListScreenView(
        state = LocationListState.Data(
            locations = listOf(
                LocationBo(
                    id = 1,
                    name = "Post-Apocalyptic Earth",
                    type = "Planet",
                    dimension = "Post-Apocalyptic Earth",
                    residents = listOf(),
                    url = "https://rickandmortyapi.com/api/location/1",
                    created = "2017-11-10T12:42:04.162Z"
                ),
                LocationBo(
                    id = 1,
                    name = "Earth",
                    type = "Planet",
                    dimension = "Dimension C-137",
                    residents = listOf(),
                    url = "https://rickandmortyapi.com/api/location/1",
                    created = "2017-11-10T12:42:04.162Z"
                ),
                LocationBo(
                    id = 1,
                    name = "Earth",
                    type = "Planet",
                    dimension = "C-137",
                    residents = listOf(),
                    url = "https://rickandmortyapi.com/api/location/1",
                    created = "2017-11-10T12:42:04.162Z"
                ),
                LocationBo(
                    id = 1,
                    name = "Earth",
                    type = "Planet",
                    dimension = "Dimension C-137",
                    residents = listOf(),
                    url = "https://rickandmortyapi.com/api/location/1",
                    created = "2017-11-10T12:42:04.162Z"
                )
            )
        ),
        onRefreshAction = {},
    )
}
