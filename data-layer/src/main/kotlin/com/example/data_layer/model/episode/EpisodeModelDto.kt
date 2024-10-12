package com.example.data_layer.model.episode
data class EpisodeResultDto(
    val info: EpisodeInfoDto? = null,
    val results: List<EpisodeDto?>? = null
)
data class EpisodeInfoDto(
    val count: Int? = null,
    val pages: Int? = null,
    val next: String? = null,
    val prev: Any? = null
)
data class EpisodeDto(
    val id: Int? = null,
    val name: String? = null,
    val air_date: String? = null,
    val episode: String? = null,
    val characters: List<String?>? = null,
    val url: String? = null,
    val created: String? = null
)
