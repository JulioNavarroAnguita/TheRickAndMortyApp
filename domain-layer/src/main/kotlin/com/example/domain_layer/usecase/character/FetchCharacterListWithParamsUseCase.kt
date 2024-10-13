package com.example.domain_layer.usecase.character

import com.example.domain_layer.utils.CharacterRepository
import com.example.domain_layer.utils.EpisodeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCharacterListWithParamsUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun fetchCharacterListWithParams(path: String) = flow { emit(characterRepository.fetchCharacterListWithParams(path)) }

}