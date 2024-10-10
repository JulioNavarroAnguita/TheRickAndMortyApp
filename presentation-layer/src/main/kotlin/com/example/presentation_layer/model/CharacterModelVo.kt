package com.example.presentation_layer.model


data class ResultVo(
    val info: InfoVo,
    val characterList: List<CharacterVo>
)

data class InfoVo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)

data class LocationVo(
    val name: String,
    val url: String
)

data class OriginVo(
    val name: String,
    val url: String
)

data class CharacterVo(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationVo,
    val name: String,
    val origin: OriginVo,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

sealed class FailureVo {
    object NoNetwork : FailureVo()
    class EmptyResponse(val message: String) : FailureVo()
    class ServerError(val code: Int, val message: String) : FailureVo()
    class ClientError(val code: Int, val message: String) : FailureVo()
    class UnexpectedFailure(val code: Int, val localizedMessage: String) : FailureVo()
    object Unknown : FailureVo()

}