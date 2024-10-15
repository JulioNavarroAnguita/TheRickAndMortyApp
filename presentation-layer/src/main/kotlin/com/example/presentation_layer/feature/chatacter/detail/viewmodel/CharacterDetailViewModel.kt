package com.example.presentation_layer.feature.chatacter.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.episode.EpisodeBo
import com.example.domain_layer.usecase.character.FetchCharacterDetailUseCase
import com.example.domain_layer.usecase.episode.FetchEpisodeListUseCase
import com.example.domain_layer.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val fetchCharacterDetailUseCase: FetchCharacterDetailUseCase,
    private val fetchEpisodeListUseCase: FetchEpisodeListUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<CharacterDetailScreenState>(CharacterDetailScreenState.Loading)
    val state: StateFlow<CharacterDetailScreenState> get() = _state.asStateFlow()

    companion object {
        const val SLASH = "/"
        const val BRACKET_OPEN = "["
        const val BRACKET_CLOSE = "]"
        const val SEPARATOR = ","
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchCharacterDetail(id: Int?) {
        id?.let {
            viewModelScope.launch {
                fetchCharacterDetailUseCase.fetchCharacterDetail(id = it)
                    .catch { error ->
                        _state.update {
                            CharacterDetailScreenState.Error(
                                message = error.message ?: "Unknown error"
                            )
                        }
                    }.flatMapConcat { characterResult ->
                        when (characterResult) {
                            is Either.Error -> {
                                flowOf(characterResult to Either.Error(error = "Error to get character detail"))
                            }

                            is Either.Success -> {
                                val numberOfEpisodeList = mutableListOf<String>()
                                characterResult.data.episodes.map { episode ->
                                    numberOfEpisodeList.add(episode.split(SLASH).last())
                                }
                                fetchEpisodeListUseCase.fetchEpisodeList(
                                    path = BRACKET_OPEN + numberOfEpisodeList.joinToString(
                                        SEPARATOR
                                    ) + BRACKET_CLOSE
                                ).catch { error ->
                                    _state.update {
                                        CharacterDetailScreenState.Error(
                                            message = error.message ?: "Unknown error"
                                        )
                                    }
                                }.map { episodeResult ->
                                    characterResult to episodeResult
                                }
                            }
                        }
                    }.collectLatest { (characterResult, episodeResult) ->
                        when (characterResult) {
                            is Either.Error -> {
                                _state.update {
                                    CharacterDetailScreenState.Error(message = "Error to get character detail")
                                }
                            }

                            is Either.Success -> {
                                when (episodeResult) {
                                    is Either.Error -> {
                                        _state.update {
                                            CharacterDetailScreenState.Data(character = characterResult.data)
                                        }
                                    }

                                    is Either.Success -> {
                                        _state.update {
                                            CharacterDetailScreenState.Data(
                                                character = characterResult.data,
                                                episodes = episodeResult.data
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
}

sealed class CharacterDetailScreenState {
    data object Loading : CharacterDetailScreenState()
    data class Error(val message: String? = null) : CharacterDetailScreenState()
    data class Data(val character: CharacterBo? = null, val episodes: List<EpisodeBo>? = null) : CharacterDetailScreenState()
}
