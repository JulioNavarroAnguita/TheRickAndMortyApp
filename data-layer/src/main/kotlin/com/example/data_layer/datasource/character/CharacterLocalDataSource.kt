package com.example.data_layer.datasource.character

import com.example.domain_layer.model.character.CharacterBo
import javax.inject.Inject


class CharacterLocalDataSource @Inject constructor() {

    private val cacheList = mutableMapOf<String, List<CharacterBo>>()
    private val characterCache = mutableMapOf<Int, CharacterBo>()

    fun saveCharacterListToCache(key: String, data: List<CharacterBo>) {
        cacheList[key] = data
    }

    fun getCharacterListFromCache(key: String): List<CharacterBo>? {
        return cacheList[key]
    }

    fun clearCharacterListCache() {
        cacheList.clear()
    }

    fun saveCharacterToCache(characterId: Int, character: CharacterBo) {
        characterCache[characterId] = character
    }

    fun getCharacterFromCache(characterId: Int): CharacterBo? {
        return characterCache[characterId]
    }

    fun clearCharacterCache(characterId: Int) {
        characterCache.remove(characterId)
    }

}