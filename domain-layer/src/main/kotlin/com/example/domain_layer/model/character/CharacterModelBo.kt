package com.example.domain_layer.model.character

data class ResultBo(
    val info: InfoBo,
    val characterList: List<CharacterBo>
)

data class InfoBo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)

data class LocationBo(
    val name: String,
    val url: String
)

data class OriginBo(
    val name: String,
    val url: String
)

data class CharacterBo(
    val created: String,
    val episodes: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationBo,
    val name: String,
    val origin: OriginBo,
    val species: String,
    val status: CharacterStatus,
    val type: String,
    val url: String
)

enum class CharacterStatus(val value : String) {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("Unknown"),
    ALL("All");
}


sealed class FailureBo {
    object NoNetwork : FailureBo()
    class EmptyResponse(val message: String) : FailureBo()
    class ServerError(val code: Int, val message: String) : FailureBo()
    class ClientError(val code: Int, val message: String) : FailureBo()
    class UnexpectedFailure(val code: Int, val localizedMessage: String) : FailureBo()
    object Unknown : FailureBo()

}