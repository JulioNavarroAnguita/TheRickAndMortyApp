package com.example.data_layer.model.episode

import com.example.data_layer.model.common.DEFAULT_INTEGER
import com.example.data_layer.model.common.DEFAULT_STRING
import com.example.domain_layer.model.episode.EpisodeBo

fun List<EpisodeDto>.episodeListDtoToBo() = map {
    it.episodeDtoToBo()
}

fun EpisodeDto.episodeDtoToBo() = EpisodeBo(
    created = created ?: DEFAULT_STRING,
    id = id ?: DEFAULT_INTEGER,
    name = name ?: DEFAULT_STRING,
    url = url ?: DEFAULT_STRING,
    airDate = airDate ?: DEFAULT_STRING,
    episode = episode ?: DEFAULT_STRING,
    characters = characters ?: listOf()
)