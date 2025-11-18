package com.threegap.bitnagil.domain.address.repository

import com.threegap.bitnagil.domain.address.model.CurrentLocation

interface AddressRepository {
    suspend fun fetchCurrentLocation(): Result<CurrentLocation>
    suspend fun fetchCurrentAddress(latitude: Double, longitude: Double): Result<String>
}
