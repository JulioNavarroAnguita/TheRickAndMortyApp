package com.example.data_layer.repository

import com.example.data_layer.datasource.CharacterRemoteDataSource
import com.example.data_layer.model.FailureDto
import com.example.data_layer.model.characterDtoToBo
import com.example.data_layer.model.characterListDtoToBo
import com.example.data_layer.model.failureDtoToBo
import com.example.domain_layer.model.CharacterBo
import com.example.domain_layer.model.FailureBo
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

    override suspend fun filterCharacterListByStatusUseCase(characterStatus: String) = try {
        val queryParams = mutableMapOf<String, String>()
        queryParams["status"] = characterStatus
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


}