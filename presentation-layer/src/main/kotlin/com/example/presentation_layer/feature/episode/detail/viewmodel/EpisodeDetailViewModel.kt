package com.example.presentation_layer.feature.episode.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.episode.EpisodeBo
import com.example.domain_layer.usecase.character.FetchCharactersFromEpisodeUseCase
import com.example.domain_layer.usecase.episode.FetchEpisodeDetailUseCase
import com.example.domain_layer.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val fetchEpisodeDetailUseCase: FetchEpisodeDetailUseCase,
    private val fetchCharactersFromEpisodeUseCase: FetchCharactersFromEpisodeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EpisodeDetailState>(EpisodeDetailState.Loading)
    val state: StateFlow<EpisodeDetailState> get() = _state.asStateFlow()

    companion object {
        const val SLASH = "/"
        const val SEPARATOR = ","
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchEpisodeDetail(episodeId: Int) {
        viewModelScope.launch {
            try {
                fetchEpisodeDetailUseCase.fetchEpisodeDetail(id = episodeId)
                    .flatMapConcat { episodeResult ->
                        when (episodeResult) {
                            is Either.Error -> {
                                flowOf(episodeResult to Either.Error(error = "Error to get episode detail"))
                            }

                            is Either.Success -> {
                                val numberOfCharacterIdList =
                                    episodeResult.data.characters.map { episode ->
                                        episode.split(SLASH).last()
                                    }
                                fetchCharactersFromEpisodeUseCase.fetchEpisodeCharacters(
                                    path = numberOfCharacterIdList.joinToString(SEPARATOR)
                                ).map { characterResult ->
                                    episodeResult to characterResult
                                }
                            }
                        }
                    }.collectLatest { (episodeResult, characterResult) ->
                        when (episodeResult) {
                            is Either.Error -> {
                                _state.update {
                                    EpisodeDetailState.Error(message = "Error to get episode detail")
                                }
                            }

                            is Either.Success -> {
                                when (characterResult) {
                                    is Either.Error -> {
                                        _state.update {
                                            EpisodeDetailState.Data(episode = episodeResult.data)
                                        }
                                    }

                                    is Either.Success -> {
                                        _state.update {
                                            EpisodeDetailState.Data(
                                                episode = episodeResult.data,
                                                characters = characterResult.data
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
            } catch (exception: Exception) {
                _state.update {
                    EpisodeDetailState.Error(
                        message = exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}

sealed class EpisodeDetailState {
    data object Loading : EpisodeDetailState()
    data class Error(val message: String) : EpisodeDetailState()
    data class Data(
        val episode: EpisodeBo,
        val characters: List<CharacterBo> = emptyList(),
    ) : EpisodeDetailState()
}
