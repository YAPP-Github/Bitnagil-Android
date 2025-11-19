package com.threegap.bitnagil.data.address.datasourceImpl

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.threegap.bitnagil.data.address.datasource.LocationDataSource
import com.threegap.bitnagil.domain.address.model.CurrentLocation
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
) : LocationDataSource {

    @SuppressLint("MissingPermission")
    override suspend fun fetchCurrentLocation(): Result<CurrentLocation> {
        return runCatching {
            val location = fusedLocationClient.lastLocation.await()
                ?: throw Exception("Location not available")

            CurrentLocation(latitude = location.latitude, longitude = location.longitude)
        }
    }
}
