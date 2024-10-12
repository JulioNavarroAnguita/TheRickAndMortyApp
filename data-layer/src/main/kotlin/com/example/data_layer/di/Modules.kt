package com.example.data_layer.di

import com.example.data_layer.datasource.character.CharacterRemoteDataSource
import com.example.data_layer.datasource.episode.EpisodeRemoteDataSource
import com.example.data_layer.repository.character.CharacterRepositoryImpl
import com.example.data_layer.repository.episode.EpisodeRepositoryImpl
import com.example.data_layer.service.RickAndMortyService.*
import com.example.domain_layer.utils.CharacterRepository
import com.example.domain_layer.utils.EpisodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun provideRetrofitApi(): Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Character
    @Provides
    @Singleton
    fun provideCharacterApiService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun provideCharacterRemoteDataSource(characterService: CharacterService) =
        CharacterRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideCharacterRepository(characterRemoteDataSource: CharacterRemoteDataSource): CharacterRepository =
        CharacterRepositoryImpl(characterRemoteDataSource = characterRemoteDataSource)

    // Episode

    @Provides
    @Singleton
    fun provideEpisodeApiService(retrofit: Retrofit): EpisodeService =
        retrofit.create(EpisodeService::class.java)

    @Provides
    @Singleton
    fun provideEpisodeRemoteDataSource(episodeService: EpisodeService) =
        EpisodeRemoteDataSource(episodeService)

    @Singleton
    @Provides
    fun provideEpisodeRepository(episodeRemoteDataSource: EpisodeRemoteDataSource): EpisodeRepository =
        EpisodeRepositoryImpl(episodeRemoteDataSource = episodeRemoteDataSource)

}