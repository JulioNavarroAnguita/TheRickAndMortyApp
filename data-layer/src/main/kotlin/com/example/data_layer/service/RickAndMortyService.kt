package com.example.data_layer.service

import com.example.data_layer.model.character.CharacterDto
import com.example.data_layer.model.character.CharacterResultDto
import com.example.data_layer.model.episode.EpisodeDto
import com.example.data_layer.model.episode.EpisodeResultDto
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

        @GET("character/{ids}")
        suspend fun getMultipleCharacters(
            @Path("ids") ids: String
        ): Response<List<CharacterDto>>

    }
    interface EpisodeService {
        @GET("episode/")
        suspend fun getEpisodes(
        ): Response<EpisodeResultDto>

        @GET("episode/{ids}")
        suspend fun getMultipleEpisodes(
            @Path("ids") ids: String
        ): Response<List<EpisodeDto>>

        @GET("episode/{id}")
        suspend fun getEpisodeById(@Path(value = "id") id: Int): Response<EpisodeDto>

    }
}
