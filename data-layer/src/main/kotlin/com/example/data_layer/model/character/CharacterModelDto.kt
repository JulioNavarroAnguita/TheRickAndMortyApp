package com.example.data_layer.model.character

import com.google.gson.annotations.SerializedName

data class CharacterResultDto(
    val info: InfoDto? = null,
    @SerializedName("results") val characterList: List<CharacterDto>? = null
)

data class InfoDto(
    val count: Int? = null,
    val next: String? = null,
    val pages: Int? = null,
    val prev: String? = null
)

data class LocationDto(
    val name: String? = null,
    val url: String? = null
)

data class OriginDto(
    val name: String? = null,
    val url: String? = null
)

data class CharacterDto(
    val created: String? = null,
    val episode: List<String>? = null,
    val gender: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val location: LocationDto? = null,
    val name: String? = null,
    val origin: OriginDto? = null,
    val species: String? = null,
    val status: String? = null,
    val type: String? = null,
    val url: String? = null
)

sealed class FailureDto {
    object NoNetwork : FailureDto()
    class EmptyResponse(val message: String?) : FailureDto()
    class ServerError(val code: Int, val message: String?) : FailureDto()
    class ClientError(val code: Int, val message: String?) : FailureDto()
    class UnexpectedFailure(val code: Int, val localizedMessage: String?) : FailureDto()
    object Unknown : FailureDto()

}