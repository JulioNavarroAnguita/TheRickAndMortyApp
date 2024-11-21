package com.example.data_layer.datasource.character

import com.example.data_layer.model.character.CharacterDto
import com.example.data_layer.model.character.CharacterResultDto
import retrofit2.Response

interface CharacterDataSource {

    suspend fun getCharactersFromService(): Response<CharacterResultDto>
    suspend fun getCharactersFilteredFromService(filterOptions: Map<String, String>? = null): Response<CharacterResultDto>
    suspend fun filterCharactersByNameFromService(name: Map<String, String>): Response<CharacterResultDto>
    suspend fun getCharacterFromService(id: Int): Response<CharacterDto>
    suspend fun fetchEpisodeCharactersFromService(path: String): Response<List<CharacterDto>>

}