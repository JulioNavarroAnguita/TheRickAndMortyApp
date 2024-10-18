package com.example.data_layer.model.character

import com.example.data_layer.model.common.DEFAULT_INTEGER
import com.example.data_layer.model.common.DEFAULT_STRING
import com.example.domain_layer.model.character.CharacterBo
import com.example.domain_layer.model.character.CharacterStatus
import com.example.domain_layer.model.character.CharacterLocationBo
import com.example.domain_layer.model.character.CharacterOriginBo

fun List<CharacterDto>.characterListDtoToBo() = map {
    it.characterDtoToBo()
}

fun CharacterDto.characterDtoToBo() = CharacterBo(
    created = created ?: DEFAULT_STRING,
    episodes = episode ?: listOf(),
    gender = gender ?: DEFAULT_STRING,
    id = id ?: DEFAULT_INTEGER,
    image = image ?: DEFAULT_STRING,
    location = location?.locationDtoToBo() ?: dummyCharacterLocationBo,
    name = name ?: DEFAULT_STRING,
    origin = origin?.originDtoToBo() ?: dummyCharacterOriginBo,
    species = species ?: DEFAULT_STRING,
    status = fromString(status) ?: CharacterStatus.UNKNOWN,
    type = type ?: DEFAULT_STRING,
    url = url ?: DEFAULT_STRING
)

fun fromString(value: String?) = CharacterStatus.values().find { it.value.equals(value, ignoreCase = true) } // TODO: extensions Utils

fun CharacterLocationDto.locationDtoToBo() = CharacterLocationBo(
    name = name ?: DEFAULT_STRING,
    url = url ?: DEFAULT_STRING
)

fun CharacterOriginDto.originDtoToBo() = CharacterOriginBo(
    name = name ?: DEFAULT_STRING,
    url = url ?: DEFAULT_STRING
)

val dummyCharacterLocationBo = CharacterLocationBo(
    name = DEFAULT_STRING,
    url = DEFAULT_STRING
)

val dummyCharacterOriginBo = CharacterOriginBo(
    name = DEFAULT_STRING,
    url = DEFAULT_STRING
)