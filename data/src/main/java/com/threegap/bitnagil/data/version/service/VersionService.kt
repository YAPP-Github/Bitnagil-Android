package com.threegap.bitnagil.data.version.service

import com.threegap.bitnagil.data.version.model.response.VersionCheckResponseDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VersionService {
    @GET("/api/v1/version/android/check")
    suspend fun checkVersion(
        @Query("major") majorVersion: Int,
        @Query("minor") minorVersion: Int,
        @Query("patch") patchVersion: Int,
    ): BaseResponse<VersionCheckResponseDto>
}
