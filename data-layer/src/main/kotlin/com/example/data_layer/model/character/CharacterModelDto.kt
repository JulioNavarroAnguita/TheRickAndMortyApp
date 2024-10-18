package com.example.data_layer.model.character

import com.example.data_layer.model.common.InfoDto
import com.google.gson.annotations.SerializedName

data class CharacterResultDto(
    val info: InfoDto? = null,
    @SerializedName("results") val characterList: List<CharacterDto>? = null
)
data class CharacterLocationDto(
    val name: String? = null,
    val url: String? = null
)
data class CharacterOriginDto(
    val name: String? = null,
    val url: String? = null
)

data class CharacterDto(
    val created: String? = null,
    val episode: List<String>? = null,
    val gender: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val location: CharacterLocationDto? = null,
    val name: String? = null,
    val origin: CharacterOriginDto? = null,
    val species: String? = null,
    val status: String? = null,
    val type: String? = null,
    val url: String? = null
)