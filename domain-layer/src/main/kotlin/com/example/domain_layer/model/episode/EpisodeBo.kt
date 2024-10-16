package com.example.domain_layer.model.episode

data class ResultBo(
    val info: EpisodeInfoBo,
    val results: List<EpisodeBo>
)

data class EpisodeInfoBo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: Any
)

data class EpisodeBo(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)