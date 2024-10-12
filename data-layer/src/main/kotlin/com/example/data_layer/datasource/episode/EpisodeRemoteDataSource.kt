package com.example.data_layer.datasource.episode

import com.example.data_layer.service.RickAndMortyService
import javax.inject.Inject

class EpisodeRemoteDataSource @Inject constructor(private val episodeService: RickAndMortyService.EpisodeService) :
    EpisodeDataSource {
    override suspend fun getEpisodeListFromService() = episodeService.getEpisodes()

    override suspend fun getEpisodeFromService(id: Int) =
        episodeService.getEpisodeById(id = id)

    override suspend fun getMultipleEpisodesFromService(path: String) =
        episodeService.getMultipleEpisodes(path)


}