package com.example.data_layer.model

import com.example.domain_layer.model.CharacterBo
import com.example.domain_layer.model.CharacterStatus
import com.example.domain_layer.model.FailureBo
import com.example.domain_layer.model.LocationBo
import com.example.domain_layer.model.OriginBo

const val DEFAULT_STRING = "none"
const val DEFAULT_INTEGER = -1
fun List<CharacterDto>.characterListDtoToBo() = map {
    it.characterDtoToBo()
}

fun CharacterDto.characterDtoToBo() = CharacterBo(
    created = created ?: DEFAULT_STRING,
    episode = episode ?: listOf(),
    gender = gender ?: DEFAULT_STRING,
    id = id ?: DEFAULT_INTEGER,
    image = image ?: DEFAULT_STRING,
    location = location?.locationDtoToBo() ?: dummyLocationBo,
    name = name ?: DEFAULT_STRING,
    origin = origin?.originDtoToBo() ?: dummyOriginBo,
    species = species ?: DEFAULT_STRING,
    status = fromString(status) ?: CharacterStatus.UNKNOWN,
    type = type ?: DEFAULT_STRING,
    url = url ?: DEFAULT_STRING
)

fun fromString(value: String?) = CharacterStatus.values().find { it.value.equals(value, ignoreCase = true) } // TODO: extensions Utils

fun LocationDto.locationDtoToBo() = LocationBo(
    name = name ?: DEFAULT_STRING,
    url = url ?: DEFAULT_STRING
)

fun OriginDto.originDtoToBo() = OriginBo(
    name = name ?: DEFAULT_STRING,
    url = url ?: DEFAULT_STRING
)

val dummyLocationBo = LocationBo(
    name = DEFAULT_STRING,
    url = DEFAULT_STRING
)

val dummyOriginBo = OriginBo(
    name = DEFAULT_STRING,
    url = DEFAULT_STRING
)

fun FailureDto.failureDtoToBo(): FailureBo = when (this) {
    is FailureDto.ClientError -> FailureBo.ClientError(code, message ?: DEFAULT_STRING)
    is FailureDto.EmptyResponse -> FailureBo.EmptyResponse(message ?: DEFAULT_STRING)
    FailureDto.NoNetwork -> FailureBo.NoNetwork
    is FailureDto.ServerError -> FailureBo.ServerError(code, message ?: DEFAULT_STRING)
    is FailureDto.UnexpectedFailure -> FailureBo.UnexpectedFailure(code, localizedMessage ?: DEFAULT_STRING)
    FailureDto.Unknown -> FailureBo.Unknown
}