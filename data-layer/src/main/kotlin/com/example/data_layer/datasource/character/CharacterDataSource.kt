package com.example.data_layer.datasource.character

import com.example.data_layer.model.character.CharacterDto
import com.example.data_layer.model.character.CharacterResultDto
import com.example.data_layer.model.episode.EpisodeDto
import retrofit2.Response

interface CharacterDataSource {

    suspend fun getCharactersFromService(): Response<CharacterResultDto>
    suspend fun getCharactersFromServiceByFilter(filterOptions: Map<String, String>): Response<CharacterResultDto>
    suspend fun getCharacterFromService(id: Int): Response<CharacterDto>
    suspend fun getMultipleCharactersFromService(path: String): Response<List<CharacterDto>>

}