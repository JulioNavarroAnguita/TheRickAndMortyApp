package com.example.data_layer.repository.character

import com.example.data_layer.datasource.character.CharacterRemoteDataSource
import com.example.data_layer.model.character.FailureDto
import com.example.data_layer.model.character.characterDtoToBo
import com.example.data_layer.model.character.characterListDtoToBo
import com.example.data_layer.model.character.failureDtoToBo
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.FailureBo
import com.example.domain_layer.utils.CharacterRepository
import com.example.domain_layer.utils.Either
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterRemoteDataSource: CharacterRemoteDataSource) :
    CharacterRepository {
    override suspend fun fetchCharacters() = try {
        val response = characterRemoteDataSource.getCharactersFromService()
        if (response.isSuccessful) {
            response.body()?.characterList?.characterListDtoToBo()?.let { characterList ->
                Either.Success(data = characterList)
            } ?: run { Either.Error(FailureDto.UnexpectedFailure(code = response.code(), localizedMessage = response.message()).failureDtoToBo()) }
        } else {
            Either.Error(FailureDto.UnexpectedFailure(code = response.code(), localizedMessage = response.message()).failureDtoToBo())
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }

    override suspend fun filterCharacterListByStatusUseCase(params: String) = try {
        val queryParams = mutableMapOf<String, String>()
        queryParams["status"] = params
        val response = characterRemoteDataSource.getCharactersFromServiceByFilter(filterOptions = queryParams)
        if (response.isSuccessful) {
            response.body()?.characterList?.characterListDtoToBo()?.let { characterList ->
                Either.Success(data = characterList)
            } ?: run { Either.Error(FailureDto.UnexpectedFailure(code = response.code(), localizedMessage = response.message()).failureDtoToBo()) }
        } else {
            Either.Error(FailureDto.UnexpectedFailure(code = response.code(), localizedMessage = response.message()).failureDtoToBo())
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }

    override suspend fun fetchCharacter(id: Int): Either<FailureBo, CharacterBo> = try {
        val response = characterRemoteDataSource.getCharacterFromService(id = id) // TODO: arreglar esto haciendo mapping hacia dto
        if (response.isSuccessful) {
            response.body()?.characterDtoToBo()?.let { character ->
                Either.Success(data = character)
            } ?: run { Either.Error(FailureDto.UnexpectedFailure(code = response.code(), localizedMessage = response.message()).failureDtoToBo()) }
        } else {
            Either.Error(FailureDto.UnexpectedFailure(code = response.code(), localizedMessage = response.message()).failureDtoToBo())
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }

    override suspend fun fetchCharacterListWithParams(path: String) = try {
        val response = characterRemoteDataSource.getMultipleCharactersFromService(path)
        if (response.isSuccessful) {
            response.body()?.characterListDtoToBo()?.let { episodeList ->
                Either.Success(data = episodeList)
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
                FailureDto.UnexpectedFailure(
                    code = response.code(),
                    localizedMessage = response.message()
                ).failureDtoToBo()
            )
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }


}