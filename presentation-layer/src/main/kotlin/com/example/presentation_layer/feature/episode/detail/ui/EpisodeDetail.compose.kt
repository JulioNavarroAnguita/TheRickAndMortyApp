import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.domain_layer.model.episode.EpisodeBo
import com.example.presentation.R
import com.example.presentation_layer.feature.episode.detail.viewmodel.EpisodeDetailState
import com.example.presentation_layer.ui.theme.Gray
import com.example.presentation_layer.ui.theme.White80

@Composable
fun EpisodeDetailScreenView(
    episodeDetailState: EpisodeDetailState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (episodeDetailState) {
            is EpisodeDetailState.Data -> EpisodeDetailData(episodeDetailState = episodeDetailState)
            is EpisodeDetailState.Error -> {
                // manejar error
            }

            EpisodeDetailState.Loading -> {
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
//    }
}

@Composable
fun EpisodeDetailData(episodeDetailState: EpisodeDetailState.Data) {
    EpisodeHeaderDetail(episode = episodeDetailState.episode)
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 8.dp),
        color = MaterialTheme.colorScheme.secondary
    )
    with(episodeDetailState.characters) {
        if (this.isNotEmpty()) {
            Text(
                text = stringResource(R.string.characters),
                style = MaterialTheme.typography.headlineMedium
            )
            EpisodeBodyDetail(characterList = this)
        }
    }
}

@Composable
fun CharacterItem(character: CharacterBo) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ), modifier = Modifier
            .fillMaxWidth()
            .width(230.dp)
            .height(230.dp)
    ) {
        val image = character.image
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .logger(DebugLogger())
            .build()
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .scale(Scale.FIT)
                    .diskCacheKey(image)
                    .error(R.drawable.error_image)
                    .build(),
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(Gray.copy(alpha = 0.35f))
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = character.name,
                    color = White80,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

    }
}

@Composable
fun EpisodeBodyDetail(characterList: List<CharacterBo>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(characterList) { character ->
            CharacterItem(character)
        }
    }
}

@Composable
fun EpisodeHeaderDetail(episode: EpisodeBo) {
    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = episode.episode, style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = episode.airDate, style = MaterialTheme.typography.titleMedium
            )
        }
        Text(
            text = episode.name, style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewEpisode() {
    EpisodeDetailScreenView(
        episodeDetailState = EpisodeDetailState.Data(
            episode = EpisodeBo(
                id = 1,
                name = "Pilot",
                airDate = "December 2, 2013",
                episode = "S01E01",
                characters = listOf(),
                url = "",
                created = ""
            ), characters = listOf(
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
        )
    )
}