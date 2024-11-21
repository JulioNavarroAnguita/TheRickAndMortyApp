package com.example.data_layer.repository.location

import com.example.data_layer.datasource.location.LocationRemoteDataSource
import com.example.data_layer.model.common.FailureDto
import com.example.data_layer.model.common.failureDtoToBo
import com.example.data_layer.model.location.toLocationBo
import com.example.data_layer.model.location.toLocationListBo
import com.example.domain_layer.utils.Either
import com.example.domain_layer.utils.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val locationRemoteDataSource: LocationRemoteDataSource) :
    LocationRepository {
    override suspend fun fetchAllLocationList() = try {
        val response = locationRemoteDataSource.getLocationsFromService()
        if (response.isSuccessful) {
            response.body()?.locationList?.toLocationListBo()?.let { locationList ->
                Either.Success(data = locationList)
            } ?: run {
                Either.Error(
                    FailureDto.UnexpectedFailure(
                        code = response.code(),
                        localizedMessage = response.message()
                    ).failureDtoToBo()
                )
            }
        } else {
            Either.Error(
                FailureDto.UnexpectedFailure(
                    code = response.code(),
                    localizedMessage = response.message()
                ).failureDtoToBo()
            )
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }

    override suspend fun fetchLocation(id: Int) = try {
        val response = locationRemoteDataSource.getLocationFromService(id = id)
        if (response.isSuccessful) {
            response.body()?.toLocationBo()?.let { location ->
                Either.Success(data = location)
            } ?: run {
                Either.Error(
                    FailureDto.UnexpectedFailure(
                        code = response.code(),
                        localizedMessage = response.message()
                    ).failureDtoToBo()
                )
            }
        } else {
            Either.Error(
                FailureDto.UnexpectedFailure(
                    code = response.code(),
                    localizedMessage = response.message()
                ).failureDtoToBo()
            )
        }
    } catch (e: Exception) {
        Either.Error(FailureDto.Unknown.failureDtoToBo())
    }

}