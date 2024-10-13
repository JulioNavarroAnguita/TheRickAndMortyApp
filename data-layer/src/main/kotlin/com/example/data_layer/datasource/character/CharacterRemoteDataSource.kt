package com.example.data_layer.datasource.character

import com.example.data_layer.service.RickAndMortyService
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val characterService: RickAndMortyService.CharacterService) :
    CharacterDataSource {
    override suspend fun getCharactersFromService() = characterService.getCharacters()
    override suspend fun getCharactersFromServiceByFilter(filterOptions: Map<String, String>) =
        characterService.getCharactersByFilter(filterOptions = filterOptions)

    override suspend fun getCharacterFromService(id: Int) =
        characterService.getCharacterById(id = id)

    override suspend fun getMultipleCharactersFromService(path: String) =
        characterService.getMultipleCharacters(path)

}