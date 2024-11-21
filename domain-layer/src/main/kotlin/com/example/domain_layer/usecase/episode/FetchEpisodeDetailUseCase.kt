package com.example.domain_layer.usecase.episode

import com.example.domain_layer.utils.EpisodeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchEpisodeDetailUseCase @Inject constructor(private val episodeRepository: EpisodeRepository) {

    fun fetchEpisodeDetail(id: Int) = flow { emit(episodeRepository.fetchEpisode(id = id)) }

}