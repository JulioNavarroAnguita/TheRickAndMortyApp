import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.domain_layer.model.episode.EpisodeBo
import com.example.presentation_layer.feature.chatacter.detail.viewmodel.CharacterDetailDataModel
import com.example.presentation_layer.ui.theme.Green40
import com.example.presentation_layer.ui.theme.Pink80
import com.example.presentation_layer.ui.theme.White80

@Composable
fun DetailScreenView(
    onBackPressed: () -> Unit,
    characterDetailDataModel: CharacterDetailDataModel,
    onEpisodeClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        HeaderDetail {
            onBackPressed()
        }
        characterDetailDataModel.character?.let { character ->
            BodyDetail(characterBo = character)
        }
        FooterDetail(characterDetailDataModel = characterDetailDataModel) { episode ->
            onEpisodeClick(episode)
        }
    }
}

@Composable
fun HeaderDetail(onBackPressed: () -> Unit) {
    MyAppBar(title = "Character Detail") {
        onBackPressed()
    }
}

@Composable
fun BodyDetail(characterBo: CharacterBo) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = characterBo.name,
                style = MaterialTheme.typography.displayMedium
            )
            Icon(
                imageVector = if (characterBo.gender == "Male") Icons.Default.Male else Icons.Default.Female,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
                tint = if (characterBo.gender == "Male") Green40 else Pink80
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.Start),
            text = characterBo.status.value,
            style = MaterialTheme.typography.titleLarge
        )
        AsyncImage(
            model = characterBo.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(256.dp)
                .clip(CircleShape)

        )
    }
}

@Composable
fun FooterDetail(characterDetailDataModel: CharacterDetailDataModel, onEpisodeClick: (Int) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        characterDetailDataModel.episodes?.let { episodes ->
            Text(
                text = "Episodes",
                style = MaterialTheme.typography.displayMedium
            )
            EpisodeList(episodes) {
                onEpisodeClick(it)
            }
        }
        characterDetailDataModel.character?.let { character ->
            Text(
                text = "Origin",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Species",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = character.species,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}

@Composable
fun EpisodeList(episodeList: List<EpisodeBo>, onEpisodeClick: (Int) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        this.items(episodeList) { episode ->
            EpisodeItem(episode) {
                onEpisodeClick(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeItem(episode: EpisodeBo, onEpisodeClick: (Int) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                BorderStroke(2.dp, Color.Black), RoundedCornerShape(8.dp)
            ),
        onClick = {
            onEpisodeClick(episode.id)
        }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            // Imagen a la izquierda
            Text(
                text = episode.episode,
                style = MaterialTheme.typography.titleMedium
            )
          /*  VerticalDivider(
                modifier = Modifier.padding(4.dp), color = MaterialTheme.colorScheme.secondary
            )*/
            Column(
            ) {
                Text(
                    text = episode.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = episode.airDate,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(title: String, onNavigationClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                style = MaterialTheme.typography.displayLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = White80
        )
    )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    DetailScreenView(
        onBackPressed = {},
        characterDetailDataModel = CharacterDetailDataModel(
            character = CharacterBo(
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
            ),
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
                    episode = "S01E10",
                    characters = listOf(),
                    url = "",
                    created = ""
                )
            )
        ),
        onEpisodeClick = {}
    )
}