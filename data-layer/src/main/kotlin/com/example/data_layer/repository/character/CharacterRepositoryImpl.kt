package com.example.data_layer.repository.character

import com.example.data_layer.datasource.character.CharacterLocalDataSource
import com.example.data_layer.datasource.character.CharacterRemoteDataSource
import com.example.data_layer.model.character.characterDtoToBo
import com.example.data_layer.model.character.characterListDtoToBo
import com.example.data_layer.model.common.FailureDto
import com.example.data_layer.model.common.failureDtoToBo
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.CharacterFetcherType
import com.example.domain_layer.model.common.FailureBo
import com.example.domain_layer.utils.CharacterRepository
import com.example.domain_layer.utils.Either
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource
) :
    CharacterRepository {

    companion object {
        private const val STATUS_TAG = "status"
        private const val NAME_TAG = "name"
        private const val EPISODE_CACHE_KEY = "episode"
    }

    override suspend fun fetchCharacters(fetcherType: CharacterFetcherType) = try {
        val cacheKey = when (fetcherType) {
            is CharacterFetcherType.All -> "All"
            is CharacterFetcherType.ByName -> "ByName:${fetcherType.name}"
            is CharacterFetcherType.Filtered -> "Filtered:${fetcherType.filter}"
        }

        characterLocalDataSource.getCharacterListFromCache(cacheKey)?.let { cachedResponse ->
            return Either.Success(data = cachedResponse)
        }

        val response = when (fetcherType) {
            is CharacterFetcherType.All -> characterRemoteDataSource.getCharactersFromService()
            is CharacterFetcherType.ByName -> characterRemoteDataSource.filterCharactersByNameFromService(
                name = mapOf(NAME_TAG to fetcherType.name)
            )

            is CharacterFetcherType.Filtered -> characterRemoteDataSource.getCharactersFilteredFromService(
                filterOptions = mapOf(STATUS_TAG to fetcherType.filter)
            )
        }
        if (response.isSuccessful) {
            response.body()?.characterList?.characterListDtoToBo()?.let { characterList ->
                characterLocalDataSource.saveCharacterListToCache(cacheKey, characterList)
                Either.Success(data = characterList)
            } ?: run {
                Either.Error(
                    FailureDto.UnexpectedFailure(
                        code = response.code(),
                        localizedMessage = response.message()
                    ).failureDtoToBo()
                )
            }
        } else {
            Either.Error(
                FailureDto.ServerError(
                    code = response.code(),
                    message = response.message()
                ).failureDtoToBo()
            )
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }

    override suspend fun fetchCharacter(id: Int): Either<FailureBo, CharacterBo> = try {
        characterLocalDataSource.getCharacterFromCache(characterId = id)?.let {
            return Either.Success(data = it)
        }
        val response = characterRemoteDataSource.getCharacterFromService(id = id)
        if (response.isSuccessful) {
            response.body()?.characterDtoToBo()?.let { character ->
                characterLocalDataSource.saveCharacterToCache(characterId = character.id, character)
                Either.Success(data = character)
            } ?: run {
                Either.Error(
                    FailureDto.UnexpectedFailure(
                        code = response.code(),
                        localizedMessage = response.message()
                    ).failureDtoToBo()
                )
            }
        } else {
            Either.Error(
                FailureDto.ServerError(
                    code = response.code(),
                    message = response.message()
                ).failureDtoToBo()
            )
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }

    override suspend fun fetchEpisodeCharacters(path: String) = try {
        characterLocalDataSource.getCharacterListFromCache("$EPISODE_CACHE_KEY$path")
            ?.let { cachedResponse ->
                return Either.Success(data = cachedResponse)
            }
        val response = characterRemoteDataSource.fetchEpisodeCharactersFromService(path)
        if (response.isSuccessful) {
            response.body()?.characterListDtoToBo()?.let { characterList ->
                characterLocalDataSource.saveCharacterListToCache(EPISODE_CACHE_KEY, characterList)
                Either.Success(data = characterList)
            } ?: run {
                Either.Error(
                    FailureDto.UnexpectedFailure(
                        code = response.code(),
                        localizedMessage = response.message()
                    ).failureDtoToBo()
                )
            }
        } else {
            Either.Error(
                FailureDto.ServerError(
                    code = response.code(),
                    message = response.message()
                ).failureDtoToBo()
            )
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }


}