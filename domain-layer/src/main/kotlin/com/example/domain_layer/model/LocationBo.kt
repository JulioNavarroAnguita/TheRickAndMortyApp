package com.example.domain_layer.model

import com.example.domain_layer.model.common.InfoBo

data class LocationResultBo(
    val info: InfoBo,
    val locationList: List<LocationBo>
) {
    data class LocationBo(
        val id: Int,
        val name: String,
        val type: String,
        val dimension: String,
        val residents: List<String>,
        val url: String,
        val created: String
    )
}