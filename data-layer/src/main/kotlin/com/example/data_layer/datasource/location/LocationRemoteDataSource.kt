package com.example.data_layer.datasource.location

import com.example.data_layer.service.RickAndMortyService.LocationService
import javax.inject.Inject

class LocationRemoteDataSource @Inject constructor(private val locationService: LocationService) :
    LocationDataSource {
    override suspend fun getLocationsFromService() = locationService.getLocations()

    override suspend fun getLocationFromService(id: Int) = locationService.getLocationById(id = id)
}