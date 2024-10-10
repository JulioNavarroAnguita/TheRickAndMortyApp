package com.example.data_layer.service

import com.example.data_layer.model.CharacterDto
import com.example.data_layer.model.ResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RickAndMortyService {
    @GET("character/")
    suspend fun getCharacters(
    ): Response<ResultDto>

    @GET("character/")
    suspend fun getCharactersByFilter(
        @QueryMap filterOptions: Map<String, String>
    ): Response<ResultDto>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path(value = "id") id: Int): Response<CharacterDto>

    /* @GET("/location")
     suspend fun getLocations(
     ): Response<CharacterResponseDTO<ResultDTO>>

     @GET("/episode")
     suspend fun getEpisodes(
     ): Response<CharacterResponseDTO<ResultDTO>>*/

}