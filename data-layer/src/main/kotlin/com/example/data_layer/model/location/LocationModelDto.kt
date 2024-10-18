package com.example.data_layer.model.location

import com.example.data_layer.model.common.InfoDto
import com.google.gson.annotations.SerializedName

data class LocationResultDto(
    val info: InfoDto? = null,
    @SerializedName("results") val locationList: List<LocationDto>? = null
)
data class LocationDto(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String>? = null,
    val url: String? = null,
    val created: String? = null
)
