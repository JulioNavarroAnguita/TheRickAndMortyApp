package com.example.data_layer.model.episode

import com.example.data_layer.model.common.InfoDto
import com.google.gson.annotations.SerializedName

data class EpisodeResultDto(
    val info: InfoDto? = null,
    @SerializedName("results") val episodeList: List<EpisodeDto>? = null
)
data class EpisodeDto(
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("air_date") val airDate: String? = null,
    val episode: String? = null,
    val characters: List<String>? = null,
    val url: String? = null,
    val created: String? = null
)
