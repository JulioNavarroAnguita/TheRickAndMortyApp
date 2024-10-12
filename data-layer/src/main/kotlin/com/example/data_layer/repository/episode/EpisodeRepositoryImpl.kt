package com.example.data_layer.repository.episode

import com.example.data_layer.datasource.episode.EpisodeRemoteDataSource
import com.example.data_layer.model.character.FailureDto
import com.example.data_layer.model.character.failureDtoToBo
import com.example.data_layer.model.episode.episodeDtoToBo
import com.example.data_layer.model.episode.episodeListDtoToBo
import com.example.domain_layer.utils.Either
import com.example.domain_layer.utils.EpisodeRepository
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(private val episodeRemoteDataSource: EpisodeRemoteDataSource) :
    EpisodeRepository {
    override suspend fun fetchEpisodeList(path: String) = try {
        val response = episodeRemoteDataSource.getMultipleEpisodesFromService(path)
        if (response.isSuccessful) {
            response.body()?.episodeListDtoToBo()?.let { episodeList ->
                Either.Success(data = episodeList)
            } ?: run {
                Either.Error(
                    FailureDto.UnexpectedFailure(
                        code = response.code(),
                        localizedMessage = response.message()
                    ).failureDtoToBo()
                )
            }
        } else {
            Either.Error(
                FailureDto.UnexpectedFailure(
                    code = response.code(),
                    localizedMessage = response.message()
                ).failureDtoToBo()
            )
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }

    override suspend fun fetchEpisode(id: Int) = try {
        val response = episodeRemoteDataSource.getEpisodeFromService(id)
        if (response.isSuccessful) {
            response.body()?.episodeDtoToBo()?.let { episode ->
                Either.Success(data = episode)
            } ?: run {
                Either.Error(
                    FailureDto.UnexpectedFailure(
                        code = response.code(),
                        localizedMessage = response.message()
                    ).failureDtoToBo()
                )
            }
        } else {
            Either.Error(
                FailureDto.UnexpectedFailure(
                    code = response.code(),
                    localizedMessage = response.message()
                ).failureDtoToBo()
            )
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())

    }
}