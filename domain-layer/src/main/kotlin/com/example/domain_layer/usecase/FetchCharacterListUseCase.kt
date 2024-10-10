package com.example.domain_layer.usecase

import com.example.domain_layer.utils.CharacterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCharacterListUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun fetchCharacterList() = flow { emit(characterRepository.fetchCharacters()) }

}