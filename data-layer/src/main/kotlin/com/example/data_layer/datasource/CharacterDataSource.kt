package com.example.data_layer.datasource

import com.example.data_layer.model.CharacterDto
import com.example.data_layer.model.ResultDto
import retrofit2.Response

interface CharacterDataSource {

    suspend fun getCharactersFromService(): Response<ResultDto>
    suspend fun getCharactersFromServiceByFilter(filterOptions: Map<String, String>): Response<ResultDto>
    suspend fun getCharacterFromService(id: Int): Response<CharacterDto>
}