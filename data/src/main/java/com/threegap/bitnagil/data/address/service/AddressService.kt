package com.threegap.bitnagil.data.address.service

import com.threegap.bitnagil.data.address.model.response.Coord2AddressResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressService {
    @GET("/v2/local/geo/coord2address.json")
    suspend fun fetchCurrentAddress(
        @Query("x") longitude: String,
        @Query("y") latitude: String,
    ): Coord2AddressResponse
}
