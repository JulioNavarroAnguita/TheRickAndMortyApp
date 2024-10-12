package com.example.domain_layer.usecase.character

import com.example.domain_layer.utils.CharacterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCharacterDetailUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun fetchCharacterDetail(id: Int) = flow { emit(characterRepository.fetchCharacter(id = id)) }

}