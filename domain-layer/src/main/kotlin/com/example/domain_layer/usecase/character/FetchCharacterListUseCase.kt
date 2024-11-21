package com.example.domain_layer.usecase.character

import com.example.domain_layer.model.character.CharacterFetcherType
import com.example.domain_layer.utils.CharacterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCharacterListUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    fun fetchCharacterList(fetcherType: CharacterFetcherType) = flow { emit(characterRepository.fetchCharacters(fetcherType = fetcherType)) }

}