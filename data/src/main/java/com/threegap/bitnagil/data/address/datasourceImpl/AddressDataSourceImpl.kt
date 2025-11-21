package com.threegap.bitnagil.data.address.datasourceImpl

import com.threegap.bitnagil.data.address.datasource.AddressDataSource
import com.threegap.bitnagil.data.address.model.response.Coord2AddressResponse
import com.threegap.bitnagil.data.address.service.AddressService
import javax.inject.Inject

class AddressDataSourceImpl @Inject constructor(
    private val addressService: AddressService,
) : AddressDataSource {

    override suspend fun fetchCurrentAddress(longitude: Double, latitude: Double): Result<Coord2AddressResponse> {
        return runCatching {
            addressService.fetchCurrentAddress(
                longitude = longitude.toString(),
                latitude = latitude.toString(),
            )
        }
    }
}
