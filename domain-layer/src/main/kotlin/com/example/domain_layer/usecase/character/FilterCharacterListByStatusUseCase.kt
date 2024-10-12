package com.example.domain_layer.usecase.character

import com.example.domain_layer.utils.CharacterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilterCharacterListByStatusUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun filterCharacterListByStatusUseCase(characterStatus: String) = flow { emit(characterRepository.filterCharacterListByStatusUseCase(characterStatus)) }

}