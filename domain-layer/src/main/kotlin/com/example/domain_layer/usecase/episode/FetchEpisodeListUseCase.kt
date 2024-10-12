package com.example.domain_layer.usecase.episode

import com.example.domain_layer.utils.EpisodeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchEpisodeListUseCase @Inject constructor(private val episodeRepository: EpisodeRepository) {

    suspend fun fetchEpisodeList(path: String) = flow { emit(episodeRepository.fetchEpisodeList(path)) }

}