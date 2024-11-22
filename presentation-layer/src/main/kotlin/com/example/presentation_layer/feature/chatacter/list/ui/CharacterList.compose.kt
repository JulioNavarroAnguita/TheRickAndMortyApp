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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.util.DebugLogger
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.CharacterLocationBo
import com.example.domain_layer.model.character.CharacterOriginBo
import com.example.domain_layer.model.character.CharacterStatus
import com.example.presentation.R
import com.example.presentation_layer.components.EmptyScreen
import com.example.presentation_layer.components.ErrorScreen
import com.example.presentation_layer.feature.chatacter.list.viewmodel.CharacterListState
import com.example.presentation_layer.ui.theme.Green40
import com.example.presentation_layer.ui.theme.PurpleGrey40
import com.example.presentation_layer.ui.theme.Red40
import com.example.presentation_layer.ui.theme.purple
import com.example.presentation_layer.utils.debounce

@Composable
fun CharacterListScreenView(
    onSearchClicked: (String) -> Unit,
    state: CharacterListState,
    onClickAction: (CharacterStatus) -> Unit,
    onClickItem: (Int) -> Unit,
    onRefreshAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is CharacterListState.Data -> CharacterListDataScreen(
                onClickAction = onClickAction,
                onClickItemAction = onClickItem,
                characterList = state.characters,
                onSearchClicked = onSearchClicked
            )

            is CharacterListState.Error -> ErrorScreen(
                onRefreshClick = onRefreshAction,
                image = R.drawable.error,
                message = R.string.error_message
            )

            CharacterListState.Loading -> {
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
    onSearchClicked: (String) -> Unit,
    onClickAction: (CharacterStatus) -> Unit,
    onClickItemAction: (Int) -> Unit,
    characterList: List<CharacterBo>
) {
    if (characterList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            ChipGroup(onClickAction)
            SearchBarCharacters(onSearchClicked = onSearchClicked)
            BodyCharacterList(
                characterList = characterList,
                onClickItem = onClickItemAction
            )
        }
    } else EmptyScreen(image = R.drawable.empty_screen, message = R.string.empty_list)

}

@Composable
fun SearchBarCharacters(onSearchClicked: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = text,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (text.isNotEmpty()) {
                    onSearchClicked(text)
                    expanded = false
                    text = ""
                }
            }
        ),
        onValueChange = {
            text = it
            expanded = text.isNotEmpty()

        },
        leadingIcon = {
            IconButton(
                onClick = {
                    onSearchClicked(text)
                    expanded = false
                    text = ""
                },
                enabled = text.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = purple
                )
            }
        },
        trailingIcon = {
            if (expanded) {
                IconButton(
                    onClick = {
                        expanded = false
                        text = ""
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = purple
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        placeholder = { Text(text = "Search") }
    )
}

@Composable
fun BodyCharacterList(characterList: List<CharacterBo>, onClickItem: (Int) -> Unit) {
    val listState = rememberLazyListState()
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

@Composable
fun StandardCard(character: CharacterBo, onClickItem: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .debounce { onClickItem(character.id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .height(120.dp)
        ) {
            val image = character.image
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .logger(DebugLogger())
                .build()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .scale(Scale.FIT)
                    .data(image)
                    .diskCacheKey(image)
                    .error(R.drawable.error_image)
                    .build(),
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.origin),
                    style = MaterialTheme.typography.titleSmall,
                    color = purple
                )
                Text(
                    text = character.origin.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.status),
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

        val chipLabels = CharacterStatus.entries.toTypedArray()
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
                    location = CharacterLocationBo(name = "", url = ""),
                    name = "Rick",
                    origin = CharacterOriginBo(name = "Earth", url = ""),
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
                    location = CharacterLocationBo(name = "Earth", url = ""),
                    name = "Morty",
                    origin = CharacterOriginBo(name = "Earth", url = ""),
                    species = "Human",
                    status = CharacterStatus.DEAD,
                    type = "",
                    url = ""
                )
            )
        ),
        onClickItem = {},
        onClickAction = {},
        onRefreshAction = {},
        onSearchClicked = {}
    )
}