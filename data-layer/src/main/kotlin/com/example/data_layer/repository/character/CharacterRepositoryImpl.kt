package com.example.data_layer.repository.character

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

class CharacterRepositoryImpl @Inject constructor(private val characterRemoteDataSource: CharacterRemoteDataSource) :
    CharacterRepository {

    companion object {
        private const val STATUS_TAG = "status"
        private const val NAME_TAG = "name"    }

    override suspend fun fetchCharacters(fetcherType: CharacterFetcherType) = try {
        val response = when (fetcherType) {
            is CharacterFetcherType.All -> characterRemoteDataSource.getCharactersFromService()
            is CharacterFetcherType.ByName -> characterRemoteDataSource.filterCharactersByNameFromService(name = mapOf(NAME_TAG to fetcherType.name))
            is CharacterFetcherType.Filtered -> characterRemoteDataSource.getCharactersFilteredFromService(filterOptions = mapOf(STATUS_TAG to fetcherType.filter))
        }
        if (response.isSuccessful) {
            response.body()?.characterList?.characterListDtoToBo()?.let { characterList ->
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
        val response =
            characterRemoteDataSource.getCharacterFromService(id = id)
        if (response.isSuccessful) {
            response.body()?.characterDtoToBo()?.let { character ->
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
        val response = characterRemoteDataSource.fetchEpisodeCharactersFromService(path)
        if (response.isSuccessful) {
            response.body()?.characterListDtoToBo()?.let { characterList ->
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