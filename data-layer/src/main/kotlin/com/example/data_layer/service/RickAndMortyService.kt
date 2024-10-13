package com.example.data_layer.service

import com.example.data_layer.model.character.CharacterDto
import com.example.data_layer.model.character.CharacterResultDto
import com.example.data_layer.model.episode.EpisodeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RickAndMortyService {
    interface CharacterService {
        @GET("character/")
        suspend fun getCharacters(
        ): Response<CharacterResultDto>

        @GET("character/")
        suspend fun getCharactersByFilter(
            @QueryMap filterOptions: Map<String, String>
        ): Response<CharacterResultDto>

        @GET("character/{id}")
        suspend fun getCharacterById(@Path(value = "id") id: Int): Response<CharacterDto>

        /* @GET("/location")
         suspend fun getLocations(
         ): Response<CharacterResponseDTO<ResultDTO>>
    */
        @GET("episode/")
        suspend fun getEpisodes(
        ): Response<List<EpisodeDto>>

        @GET("episode/{array}")
        suspend fun getMultipleEpisodes(
            @Path("array") list: List<Int>
        ): Response<List<EpisodeDto>>

        @GET("character/{ids}")
        suspend fun getMultipleCharacters(
            @Path("ids") ids: String
        ): Response<List<CharacterDto>>

        @GET("episode/{id}")
        suspend fun getEpisodeById(@Path(value = "id") id: Int): Response<EpisodeDto>

    }
    interface EpisodeService {
        @GET("episode/")
        suspend fun getEpisodes(
        ): Response<List<EpisodeDto>>

        @GET("episode/{ids}")
        suspend fun getMultipleEpisodes(
            @Path("ids") ids: String
        ): Response<List<EpisodeDto>>

        @GET("episode/{id}")
        suspend fun getEpisodeById(@Path(value = "id") id: Int): Response<EpisodeDto>

    }
}
