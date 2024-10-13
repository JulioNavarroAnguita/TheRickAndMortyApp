package com.example.domain_layer.utils

import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.FailureBo
import com.example.domain_layer.model.episode.EpisodeBo

interface CharacterRepository {
    suspend fun fetchCharacters(): Either<FailureBo, List<CharacterBo>>
    suspend fun filterCharacterListByStatusUseCase(params: String): Either<FailureBo, List<CharacterBo>>
    suspend fun fetchCharacter(id: Int): Either<FailureBo, CharacterBo>
    suspend fun fetchCharacterListWithParams(path: String): Either<FailureBo, List<CharacterBo>>

}

interface EpisodeRepository {
    suspend fun fetchEpisodeList(path: String): Either<FailureBo, List<EpisodeBo>>
    suspend fun fetchEpisode(id: Int): Either<FailureBo, EpisodeBo>
}