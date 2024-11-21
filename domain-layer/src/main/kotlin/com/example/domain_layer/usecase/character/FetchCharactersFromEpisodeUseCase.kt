package com.example.domain_layer.usecase.character

import com.example.domain_layer.utils.CharacterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCharactersFromEpisodeUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    fun fetchEpisodeCharacters(path: String) = flow { emit(characterRepository.fetchEpisodeCharacters(path)) }

}