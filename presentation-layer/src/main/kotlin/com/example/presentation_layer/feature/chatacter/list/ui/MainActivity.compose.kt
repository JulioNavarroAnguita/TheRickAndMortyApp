package com.example.presentation_layer.feature.chatacter.list.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain_layer.model.CharacterBo
import com.example.domain_layer.model.CharacterStatus
import com.example.domain_layer.model.LocationBo
import com.example.domain_layer.model.OriginBo
import com.example.presentation_layer.ui.theme.Gray
import com.example.presentation_layer.ui.theme.Green40
import com.example.presentation_layer.ui.theme.PurpleGrey40
import com.example.presentation_layer.ui.theme.Red40
import com.example.presentation_layer.ui.theme.White80
import com.example.presentation_layer.ui.theme.purple

@SuppressLint("ResourceType")
@Composable
fun ListScreen(
    characterList: List<CharacterBo>,
    onClickAction: (Map<CharacterStatus, Boolean>) -> Unit,
    onClickItem: (Int) -> Unit,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = Color.White),
    ) {
        MyAppBar("Listado") {
            onBackPressed()
        }
        ChipGroup(onClickAction)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            this.items(characterList) { character ->
                StandardCard(character = character, onClickItem = onClickItem)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(title: String, onNavigationClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                style = MaterialTheme.typography.displayLarge
            )
        },
        backgroundColor = White80, // Color de fondo de la TopAppBar
        contentColor = White80,
        elevation = 0.dp
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    ListScreen(
        characterList = listOf(
            CharacterBo(
                created = "",
                episode = listOf(),
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
                episode = listOf(),
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
        ),
        onClickAction = { mapOf<CharacterStatus, Boolean>() },
        onClickItem = {},
        onBackPressed = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardCard(character: CharacterBo, onClickItem: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = {
            onClickItem(character.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp) // Ajusta la altura según lo necesites
        ) {
            // Imagen a la izquierda
            AsyncImage(
                model = character.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )

            // Columna con datos a la derecha
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipGroup(
    onClickAction: (Map<CharacterStatus, Boolean>) -> Unit
) {
    val chipLabels = CharacterStatus.values()
    val chipStates = remember { MutableList(chipLabels.size) { false } }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        chipLabels.forEachIndexed { index, label ->
            Chip(
                onClick = {
                    chipStates[index] = !chipStates[index]
                    val stateMap = chipLabels.zip(chipStates).toMap()
                    onClickAction(stateMap)
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = if (chipStates[index]) Green40 else White80,
                    contentColor = Color.White
                ),
                border = BorderStroke(width = 1.dp, color = Gray),
                modifier = Modifier.padding(4.dp)
            ) {
                Text(label.toString())
            }
        }
    }
}