package com.example.presentation_layer.feature.episode.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.episode.EpisodeBo
import com.example.domain_layer.usecase.character.FetchCharacterListWithParamsUseCase
import com.example.domain_layer.usecase.episode.FetchEpisodeDetailUseCase
import com.example.domain_layer.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val fetchEpisodeDetailUseCase: FetchEpisodeDetailUseCase,
    private val fetchCharacterListWithParamsUseCase: FetchCharacterListWithParamsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EpisodeDetailState())
    val state: StateFlow<EpisodeDetailState> get() = _state.asStateFlow()
    fun fetchEpisodeDetail(episodeId: Int) {
        viewModelScope.launch {
            fetchEpisodeDetailUseCase.fetchEpisodeDetail(id = episodeId)
                .collect { episodeResult ->
                    when (episodeResult) {
                        is Either.Error -> {}
                        is Either.Success -> {
                            //TODO: contemplar combine
                            val numberOfCharacterList = mutableListOf<String>()
                            episodeResult.data.characters.map { episode ->
                                episode?.let {
                                    numberOfCharacterList.add(episode.split("/").last())
                                }
                            }
                            fetchCharacterListWithParamsUseCase.fetchCharacterListWithParams(
                                numberOfCharacterList.joinToString(",")
                            ).collect { characterResult ->
                                when (characterResult) {
                                    is Either.Error -> {}
                                    is Either.Success -> {
                                        _state.update {
                                            it.copy(
                                                episodeData = EpisodeDetailDataModel(
                                                    episode = episodeResult.data,
                                                    characters = characterResult.data
                                                )
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

data class EpisodeDetailState(
    val episodeData: EpisodeDetailDataModel? = null,
)

data class EpisodeDetailDataModel(
    val episode: EpisodeBo? = null,
    val characters: List<CharacterBo>? = null,
)