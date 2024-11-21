package com.example.data_layer.service

import com.example.data_layer.model.character.CharacterDto
import com.example.data_layer.model.character.CharacterResultDto
import com.example.data_layer.model.episode.EpisodeDto
import com.example.data_layer.model.episode.EpisodeResultDto
import com.example.data_layer.model.location.LocationDto
import com.example.data_layer.model.location.LocationResultDto
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
            @QueryMap filterOptions: Map<String, String>? = null
        ): Response<CharacterResultDto>

        @GET("character/{id}")
        suspend fun getCharacterById(@Path(value = "id") id: Int): Response<CharacterDto>

        @GET("character/{ids}")
        suspend fun getEpisodeCharacters(
            @Path("ids") ids: String
        ): Response<List<CharacterDto>>

        @GET("character/")
        suspend fun filterCharacters(
            @QueryMap name: Map<String, String>
        ): Response<CharacterResultDto>

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

    interface LocationService {
        @GET("location/")
        suspend fun getLocations(
        ): Response<LocationResultDto>

        @GET("location/{id}")
        suspend fun getLocationById(@Path(value = "id") id: Int): Response<LocationDto>

    }
}
