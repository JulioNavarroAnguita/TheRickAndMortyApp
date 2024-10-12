package com.example.presentation_layer.feature.episode.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain_layer.usecase.episode.FetchEpisodeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val fetchEpisodeDetailUseCase: FetchEpisodeDetailUseCase,
//    private val fetchEpisodeListUseCase: FetchEpisodeListUseCase,
) : ViewModel() {

}