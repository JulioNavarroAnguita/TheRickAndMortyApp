package com.example.domain_layer.utils

import com.example.domain_layer.model.LocationResultBo.LocationBo
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.CharacterFetcherType
import com.example.domain_layer.model.common.FailureBo
import com.example.domain_layer.model.episode.EpisodeBo

interface CharacterRepository {
    suspend fun fetchCharacters(fetcherType: CharacterFetcherType): Either<FailureBo, List<CharacterBo>>
    suspend fun fetchCharacter(id: Int): Either<FailureBo, CharacterBo>
    suspend fun fetchEpisodeCharacters(path: String): Either<FailureBo, List<CharacterBo>>
}

interface EpisodeRepository {
    suspend fun fetchEpisodeList(path: String): Either<FailureBo, List<EpisodeBo>>
    suspend fun fetchAllEpisodes(): Either<FailureBo, List<EpisodeBo>>
    suspend fun fetchEpisode(id: Int): Either<FailureBo, EpisodeBo>
}

interface LocationRepository {
    suspend fun fetchAllLocationList(): Either<FailureBo, List<LocationBo>>
    suspend fun fetchLocation(id: Int): Either<FailureBo, LocationBo>
}