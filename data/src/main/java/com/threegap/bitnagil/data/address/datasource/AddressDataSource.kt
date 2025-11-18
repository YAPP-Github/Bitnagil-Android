package com.threegap.bitnagil.data.address.datasource

import com.threegap.bitnagil.data.address.model.response.Coord2AddressResponse

interface AddressDataSource {
    suspend fun fetchCurrentAddress(longitude: Double, latitude: Double): Result<Coord2AddressResponse>
}
