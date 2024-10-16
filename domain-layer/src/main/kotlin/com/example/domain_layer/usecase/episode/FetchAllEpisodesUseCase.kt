package com.example.domain_layer.usecase.episode

import com.example.domain_layer.utils.EpisodeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FetchAllEpisodesUseCase @Inject constructor(private val episodeRepository: EpisodeRepository) {
    suspend fun fetchAllEpisodes() =
        flow { emit(episodeRepository.fetchAllEpisodes()) }

}
