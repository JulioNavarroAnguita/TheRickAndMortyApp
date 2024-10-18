package com.example.data_layer.model.location

import com.example.data_layer.model.common.DEFAULT_INTEGER
import com.example.data_layer.model.common.DEFAULT_STRING
import com.example.data_layer.model.common.toInfoBo
import com.example.domain_layer.model.LocationResultBo
import com.example.domain_layer.model.LocationResultBo.LocationBo
import com.example.domain_layer.model.common.InfoBo


fun List<LocationDto>.toLocationListBo() = map {
    it.toLocationBo()
}

fun LocationDto.toLocationBo() = LocationBo(
    id = id ?: DEFAULT_INTEGER,
    name = name ?: DEFAULT_STRING,
    type = type ?: DEFAULT_STRING,
    dimension = dimension ?: DEFAULT_STRING,
    residents = residents ?: getDummyResidents(),
    url = url ?: DEFAULT_STRING,
    created = created ?: DEFAULT_STRING

)

fun LocationResultDto.toLocationResultBo() = LocationResultBo(
    info = info?.toInfoBo() ?: getDummyInfoBo(),
    locationList = locationList?.toLocationListBo() ?: getDummyLocationListBo()
)

fun getDummyLocationListBo() = listOf(
    LocationBo(
        id = DEFAULT_INTEGER,
        name = DEFAULT_STRING,
        type = DEFAULT_STRING,
        dimension = DEFAULT_STRING,
        residents = getDummyResidents(),
        url = DEFAULT_STRING,
        created = DEFAULT_STRING
    )
)

fun getDummyResidents() = listOf(
    DEFAULT_STRING,
    DEFAULT_STRING
)

fun getDummyInfoBo() = InfoBo(
    count = DEFAULT_INTEGER,
    next = DEFAULT_STRING,
    pages = DEFAULT_INTEGER,
    prev = DEFAULT_STRING
)
