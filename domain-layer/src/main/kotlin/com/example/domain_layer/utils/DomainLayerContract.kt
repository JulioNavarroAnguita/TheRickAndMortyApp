package com.example.domain_layer.utils

import com.example.domain_layer.model.CharacterBo
import com.example.domain_layer.model.FailureBo

interface CharacterRepository {
    suspend fun fetchCharacters(): Either<FailureBo, List<CharacterBo>>
    suspend fun filterCharacterListByStatusUseCase(characterStatus: String): Either<FailureBo, List<CharacterBo>>
    suspend fun fetchCharacter(id: Int): Either<FailureBo, CharacterBo>
}