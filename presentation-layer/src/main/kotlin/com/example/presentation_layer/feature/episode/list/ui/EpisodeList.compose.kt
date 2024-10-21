package com.example.presentation_layer.feature.episode.list.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain_layer.model.episode.EpisodeBo
import com.example.presentation.R
import com.example.presentation_layer.components.EmptyScreen
import com.example.presentation_layer.components.ErrorScreen
import com.example.presentation_layer.feature.episode.list.viewmodel.EpisodeListState
import com.example.presentation_layer.ui.theme.Green40


@Composable
fun EpisodeListScreenView(
    state: EpisodeListState,
    onClickItem: (Int) -> Unit,
    onRefreshAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        when (state) {
            is EpisodeListState.Data -> CharacterListDataScreen(
                onClickItem = onClickItem,
                episodeList = state.episodes
            )

            is EpisodeListState.Error -> ErrorScreen(
                onRefreshClick = onRefreshAction,
                image = R.drawable.error,
                message = R.string.error_message
            )

            EpisodeListState.Loading -> {
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
fun CharacterListDataScreen(
    onClickItem: (Int) -> Unit,
    episodeList: List<EpisodeBo>
) {
    if (episodeList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            BodyEpisodeList(
                episodeList = episodeList,
                onClickItem = onClickItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    } else EmptyScreen(image = R.drawable.empty_screen, message = R.string.empty_list)

}

@Composable
fun BodyEpisodeList(episodeList: List<EpisodeBo>, onClickItem: (Int) -> Unit) {
    ExpandableList(
        sections = groupEpisodesBySeason(
            episodeList
        ), onClickItem = onClickItem
    )
}

fun groupEpisodesBySeason(episodeList: List<EpisodeBo>): List<SectionData> {
    val seasonMap = mutableMapOf<Int, MutableList<EpisodeBo>>()
    episodeList.map { episode ->
        val regex = Regex("""S(\d{2})""")
        regex.find(episode.episode)?.let { matchResult ->
            val seasonNumber = matchResult.groupValues[1].toInt()

            seasonMap.getOrPut(seasonNumber) {
                mutableListOf()
            }.add(episode)
        }
    }
    return seasonMap.map { (seasonNumber, episodes) ->
        SectionData(headerText = "Season $seasonNumber", items = episodes)
    }
}

@Composable
fun ExpandableCard(episode: EpisodeBo) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = episode.name, style = MaterialTheme.typography.titleMedium)
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = episode.episode)
            }
        }
    }
}

data class SectionData(val headerText: String, val items: List<EpisodeBo>)

fun LazyListScope.sectionExpandableSeason(
    sectionData: SectionData,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit,
    onClickItem: (Int) -> Unit
) {
    item {
        SectionHeader(
            text = sectionData.headerText,
            isExpanded = isExpanded,
            onHeaderClicked = onHeaderClick
        )
    }
    if (isExpanded) {
        itemsIndexed(sectionData.items) { index, item ->
            SectionItem(
                episode = item,
                onEpisodeClick = { episodeId ->
                    onClickItem(episodeId)
                }
            )
            if (index < sectionData.items.size - 1) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
fun ExpandableList(sections: List<SectionData>, onClickItem: (Int) -> Unit) {
    val expandedSections = rememberSaveable { mutableStateOf(setOf<Int>()) }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sections.onEachIndexed { index, sectionData ->
            sectionExpandableSeason(
                sectionData = sectionData,
                isExpanded = !expandedSections.value.contains(index),
                onHeaderClick = {
                    expandedSections.value = if (expandedSections.value.contains(index)) {
                        expandedSections.value - index
                    } else {
                        expandedSections.value + index
                    }
                },
                onClickItem = onClickItem
            )
        }
    }
}

@Composable
fun SectionHeader(text: String, isExpanded: Boolean, onHeaderClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(8.dp))
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { onHeaderClicked() }) {
            val icon = if (isExpanded) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            }
            Icon(
                imageVector = icon,
                contentDescription = if (isExpanded) "arrowUp" else "arrowDown"
            )
        }
    }
}

@Composable
fun SectionItem(episode: EpisodeBo, onEpisodeClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEpisodeClick(episode.id) }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = episode.episode,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Green40, shape = RoundedCornerShape(4.dp))
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(4.dp))
                .padding(4.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(top = 16.dp)
        ) {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = episode.airDate,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EpisodeListScreenPreview() {
    EpisodeListScreenView(
        state = EpisodeListState.Data(
            episodes = listOf(
                EpisodeBo(
                    id = 1,
                    name = "Pilot",
                    airDate = "April 7, 2014",
                    episode = "S01E10",
                    characters = listOf(),
                    url = "",
                    created = ""
                ),
                EpisodeBo(
                    id = 1,
                    name = "Pilot",
                    airDate = "April 7, 2014",
                    episode = "S01E10",
                    characters = listOf(),
                    url = "",
                    created = ""
                ),
                EpisodeBo(
                    id = 1,
                    name = "Pilot",
                    airDate = "April 7, 2014",
                    episode = "S02E10",
                    characters = listOf(),
                    url = "",
                    created = ""
                )
            )
        ),
        onClickItem = {},
//        onClickAction = {},
        onRefreshAction = {}
    )
}

@Preview
@Composable
fun listPreview() {
    ExpandableList(
        sections = listOf(
            SectionData(
                headerText = "Season 1",
                items = listOf(
                    EpisodeBo(
                        id = 1,
                        name = "Pilot",
                        airDate = "April 7, 2014",
                        episode = "S01E10",
                        characters = listOf(),
                        url = "",
                        created = ""
                    ),
                    EpisodeBo(
                        id = 1,
                        name = "Pilot",
                        airDate = "April 7, 2014",
                        episode = "S01E10",
                        characters = listOf(),
                        url = "",
                        created = ""
                    ),
                    EpisodeBo(
                        id = 1,
                        name = "Pilot",
                        airDate = "April 7, 2014",
                        episode = "S01E10",
                        characters = listOf(),
                        url = "",
                        created = ""
                    )
                )
            )
        ),
        onClickItem = {}
    )
}

@Preview
@Composable
fun ITemPreview() {

}