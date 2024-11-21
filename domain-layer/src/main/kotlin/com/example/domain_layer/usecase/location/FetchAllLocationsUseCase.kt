package com.example.domain_layer.usecase.location

import com.example.domain_layer.utils.LocationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchAllLocationsUseCase @Inject constructor(private val locationRepository: LocationRepository) {

    fun fetchAllLocations() = flow { emit(locationRepository.fetchAllLocationList()) }

}