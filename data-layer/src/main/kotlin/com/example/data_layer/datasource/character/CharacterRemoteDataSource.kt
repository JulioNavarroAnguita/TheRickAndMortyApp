package com.example.data_layer.datasource.character

import com.example.data_layer.service.RickAndMortyService.CharacterService
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val characterService: CharacterService) :
    CharacterDataSource {

    override suspend fun getCharactersFromService() =
        characterService.getCharacters()

    override suspend fun getCharactersFilteredFromService(filterOptions: Map<String, String>?) =
        characterService.getCharactersByFilter(filterOptions = filterOptions)

    override suspend fun filterCharactersByNameFromService(name: Map<String, String>) =
        characterService.filterCharacters(name = name)

    override suspend fun getCharacterFromService(id: Int) =
        characterService.getCharacterById(id = id)

    override suspend fun fetchEpisodeCharactersFromService(path: String) =
        characterService.getEpisodeCharacters(path)

}