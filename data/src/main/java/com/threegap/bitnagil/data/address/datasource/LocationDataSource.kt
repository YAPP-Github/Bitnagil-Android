package com.threegap.bitnagil.data.address.datasource

import com.threegap.bitnagil.domain.address.model.CurrentLocation

interface LocationDataSource {
    suspend fun fetchCurrentLocation(): Result<CurrentLocation>
}
