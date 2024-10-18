package com.example.domain_layer.model.character

import com.example.domain_layer.model.common.InfoBo

data class CharacterResultBo(
    val info: InfoBo,
    val characterList: List<CharacterBo>
)

data class CharacterLocationBo(
    val name: String,
    val url: String
)

data class CharacterOriginBo(
    val name: String,
    val url: String
)

data class CharacterBo(
    val created: String,
    val episodes: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: CharacterLocationBo,
    val name: String,
    val origin: CharacterOriginBo,
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