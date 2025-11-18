package com.threegap.bitnagil.data.address.repositoryImpl

import com.threegap.bitnagil.data.address.datasource.AddressDataSource
import com.threegap.bitnagil.data.address.datasource.LocationDataSource
import com.threegap.bitnagil.data.address.model.response.toAddress
import com.threegap.bitnagil.domain.address.model.CurrentLocation
import com.threegap.bitnagil.domain.address.repository.AddressRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val addressDataSource: AddressDataSource,
) : AddressRepository {

    override suspend fun fetchCurrentLocation(): Result<CurrentLocation> {
        return locationDataSource.fetchCurrentLocation()
    }

    override suspend fun fetchCurrentAddress(longitude: Double, latitude: Double): Result<String> {
        return addressDataSource.fetchCurrentAddress(longitude = longitude, latitude = latitude)
            .mapCatching {
                it.toAddress() ?: throw Exception("Address not found")
            }
    }
}
