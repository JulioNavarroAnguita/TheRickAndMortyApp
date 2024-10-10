package com.example.data_layer.datasource

import com.example.data_layer.service.RickAndMortyService
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val rickAndMortyService: RickAndMortyService) :
    CharacterDataSource {
    override suspend fun getCharactersFromService() = rickAndMortyService.getCharacters()
    override suspend fun getCharactersFromServiceByFilter(filterOptions: Map<String, String>) =
        rickAndMortyService.getCharactersByFilter(filterOptions = filterOptions)

    override suspend fun getCharacterFromService(id: Int) =
        rickAndMortyService.getCharacterById(id = id)

}