package com.example.data_layer.model.episode

import com.example.domain_layer.model.episode.EpisodeBo

const val DEFAULT_STRING = "none"
const val DEFAULT_INTEGER = -1
fun List<EpisodeDto>.episodeListDtoToBo() = map {
    it.episodeDtoToBo()
}

fun EpisodeDto.episodeDtoToBo() = EpisodeBo(
    created = created ?: DEFAULT_STRING,
    id = id ?: DEFAULT_INTEGER,
    name = name ?: DEFAULT_STRING,
    url = url ?: DEFAULT_STRING,
    airDate = air_date ?: DEFAULT_STRING,
    episode = episode ?: DEFAULT_STRING,
    characters = characters ?: listOf()

)