package com.example.data_layer.datasource.episode

import com.example.data_layer.model.episode.EpisodeDto
import com.example.data_layer.model.episode.EpisodeResultDto
import retrofit2.Response

interface EpisodeDataSource {

    suspend fun getEpisodeListFromService(): Response<EpisodeResultDto>
    suspend fun getEpisodeFromService(id: Int): Response<EpisodeDto>
    suspend fun getMultipleEpisodesFromService(path: String): Response<List<EpisodeDto>>
}