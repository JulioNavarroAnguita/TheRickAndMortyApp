package com.example.presentation_layer.feature.episode.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.episode.EpisodeBo
import com.example.domain_layer.usecase.episode.FetchAllEpisodesUseCase
import com.example.domain_layer.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    private val fetchAllEpisodesUseCase: FetchAllEpisodesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EpisodeListState>(EpisodeListState.Loading)
    val state: StateFlow<EpisodeListState> get() = _state.asStateFlow()

    init {
        fetchAllEpisodes()
    }
    private fun fetchAllEpisodes() {
        viewModelScope.launch {
            fetchAllEpisodesUseCase.fetchAllEpisodes()
                .catch { error ->
                    _state.update {
                        EpisodeListState.Error(error.message ?: "Unknown error")
                    }
                }
                .collectLatest { result ->
                    when (result) {
                        is Either.Error -> {
                            _state.update {
                                EpisodeListState.Error(message = "Error to load episodes")
                            }
                        }

                        is Either.Success -> {
                            _state.update {
                                EpisodeListState.Data(episodes = result.data)
                            }
                        }
                    }
                }
        }
    }
}

sealed class EpisodeListState {
    data object Loading : EpisodeListState()
    data class Error(val message: String) : EpisodeListState()
    data class Data(val episodes: List<EpisodeBo>) : EpisodeListState()
}