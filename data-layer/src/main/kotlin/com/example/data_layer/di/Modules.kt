package com.example.data_layer.di

import com.example.data_layer.datasource.CharacterRemoteDataSource
import com.example.data_layer.repository.CharacterRepositoryImpl
import com.example.data_layer.service.RickAndMortyService
import com.example.domain_layer.utils.CharacterRepository
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

    @Singleton
    @Provides
    fun provideRetrofitApi(): RickAndMortyService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rickandmortyapi.com/api/")
            .build().create(RickAndMortyService::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRemoteDataSource(apiService: RickAndMortyService) = CharacterRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideCharacterRepository(characterRemoteDataSource: CharacterRemoteDataSource): CharacterRepository =
        CharacterRepositoryImpl(characterRemoteDataSource = characterRemoteDataSource)

}