package com.example.presentation_layer.feature.chatacter.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.CharacterStatus
import com.example.domain_layer.model.character.LocationBo
import com.example.domain_layer.model.character.OriginBo
import com.example.presentation.R
import com.example.presentation_layer.components.EmptyScreen
import com.example.presentation_layer.components.ErrorScreen
import com.example.presentation_layer.feature.chatacter.list.viewmodel.CharacterListState
import com.example.presentation_layer.ui.theme.Green40
import com.example.presentation_layer.ui.theme.PurpleGrey40
import com.example.presentation_layer.ui.theme.Red40
import com.example.presentation_layer.ui.theme.White80
import com.example.presentation_layer.ui.theme.purple

@Composable
fun CharacterListScreenView(
    state: CharacterListState,
    onClickAction: (CharacterStatus) -> Unit,
    onClickItem: (Int) -> Unit,
    onRefreshAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        when (state) {
            is CharacterListState.Data -> CharacterListDataScreen(
                onClickAction = onClickAction,
                onClickItemAction = onClickItem,
                characterList = state.characters
            )

            is CharacterListState.Error -> ErrorScreen(
                onRefreshClick = onRefreshAction,
                image = R.drawable.error,
                message = R.string.error_message
            )

            CharacterListState.Loading -> CircularProgressIndicator()
        }
    }
}

@Composable
fun CharacterListDataScreen(
    onClickAction: (CharacterStatus) -> Unit,
    onClickItemAction: (Int) -> Unit,
    characterList: List<CharacterBo>
) {
    if (characterList.isNotEmpty()) {
        com.example.presentation_layer.feature.episode.list.ui.MyAppBar("Character List")
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            ChipGroup(onClickAction)
            Spacer(modifier = Modifier.height(8.dp))
            BodyCharacterList(
                characterList = characterList,
                onClickItem = onClickItemAction
            )
        }
    } else EmptyScreen(image = R.drawable.empty_screen, message = R.string.empty_list)

}

@Composable
fun BodyCharacterList(characterList: List<CharacterBo>, onClickItem: (Int) -> Unit) {
    val listState = rememberLazyListState()

    // Efecto para mover el scroll hacia arriba al actualizar la lista
    LaunchedEffect(characterList) {
        listState.scrollToItem(0) // Mover el scroll al inicio
    }
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(characterList) { character ->
            StandardCard(
                character = character,
                onClickItem = onClickItem
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                style = MaterialTheme.typography.displayLarge
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = White80
        )
    )
}

@Composable
fun StandardCard(character: CharacterBo, onClickItem: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = {
            onClickItem(character.id)
        }
    ) {
        Row(
            modifier = Modifier
                .height(120.dp)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f) // Para que ocupe más espacio
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Origin",
                    style = MaterialTheme.typography.titleSmall,
                    color = purple
                )
                Text(
                    text = character.origin.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Status",
                    style = MaterialTheme.typography.titleSmall,
                    color = purple
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(12.dp),
                        tint = when (character.status) {
                            CharacterStatus.ALIVE -> Green40
                            CharacterStatus.DEAD -> Red40
                            CharacterStatus.UNKNOWN -> PurpleGrey40
                            CharacterStatus.ALL -> Green40
                        }
                    )
                    Text(
                        text = character.status.value.plus(" - ").plus(character.species),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun ChipGroup(
    onClickAction: (CharacterStatus) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        var selectedChipIndex by remember { mutableStateOf(-1) }

        val chipLabels = CharacterStatus.values()
        chipLabels.forEachIndexed { index, label ->
            FilterChip(
                selected = selectedChipIndex == index,
                leadingIcon = if (selectedChipIndex == index) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null,
                onClick = {
                    selectedChipIndex = index
                    onClickAction(label)
                },
                label = { Text(label.value) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterListScreenPreview() {
    CharacterListScreenView(
        state = CharacterListState.Data(
            characters = listOf(
                CharacterBo(
                    created = "",
                    episodes = listOf(),
                    gender = "Male",
                    id = 1,
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    location = LocationBo(name = "", url = ""),
                    name = "Rick",
                    origin = OriginBo(name = "Earth", url = ""),
                    species = "Human",
                    status = CharacterStatus.ALIVE,
                    type = "",
                    url = ""
                ),
                CharacterBo(
                    created = "",
                    episodes = listOf(),
                    gender = "Male",
                    id = 1,
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    location = LocationBo(name = "Earth", url = ""),
                    name = "Morty",
                    origin = OriginBo(name = "Earth", url = ""),
                    species = "Human",
                    status = CharacterStatus.DEAD,
                    type = "",
                    url = ""
                )
            )
        ),
        onClickItem = {},
        onClickAction = {},
        onRefreshAction = {}
    )
}