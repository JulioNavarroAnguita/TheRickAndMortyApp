package com.example.data_layer.datasource.location

import com.example.data_layer.model.location.LocationDto
import com.example.data_layer.model.location.LocationResultDto
import retrofit2.Response

interface LocationDataSource {
    suspend fun getLocationsFromService(): Response<LocationResultDto>
    suspend fun getLocationFromService(id: Int): Response<LocationDto>
}