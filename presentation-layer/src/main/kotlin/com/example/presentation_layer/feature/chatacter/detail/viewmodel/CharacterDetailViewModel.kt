package com.example.presentation_layer.feature.chatacter.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.episode.EpisodeBo
import com.example.domain_layer.usecase.character.FetchCharacterDetailUseCase
import com.example.domain_layer.usecase.episode.FetchEpisodeListUseCase
import com.example.domain_layer.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val fetchCharacterDetailUseCase: FetchCharacterDetailUseCase,
    private val fetchEpisodeListUseCase: FetchEpisodeListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> get() = _state.asStateFlow()

    fun fetchCharacterDetail(id: Int?) {
        id?.let {
            viewModelScope.launch {
                fetchCharacterDetailUseCase.fetchCharacterDetail(id = id)
//                .onStart {  }
//                .catch {  }
                    .collect { characterResult ->
                        when (characterResult) {
                            is Either.Error -> {}
                            is Either.Success -> {
                                //TODO: contemplar combine
                                val numberOfEpisodeList = mutableListOf<String>()
                                characterResult.data.episodes.map { episode ->
                                    numberOfEpisodeList.add(episode.split("/").last())
                                }
                                fetchEpisodeListUseCase.fetchEpisodeList(
                                    numberOfEpisodeList.joinToString(
                                        ","
                                    )
                                )
                                    .collect { episodeListResult ->
                                        when (episodeListResult) {
                                            is Either.Error -> {}
                                            is Either.Success -> {
                                                _state.update {
                                                    it.copy(
                                                        characterData = CharacterDetailDataModel(
                                                            character = characterResult.data,
                                                            episodes = episodeListResult.data
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                            }

                            else -> {}
                        }

                    }
            }

        }
    }

}

data class CharacterDetailState(
    val characterData: CharacterDetailDataModel? = null,
)

data class CharacterDetailDataModel(
    val character: CharacterBo? = null,
    val episodes: List<EpisodeBo>? = null,
)