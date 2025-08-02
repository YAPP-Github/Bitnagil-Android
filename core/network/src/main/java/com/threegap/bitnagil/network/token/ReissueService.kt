package com.threegap.bitnagil.network.token

import com.threegap.bitnagil.network.model.AuthToken
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface ReissueService {
    @POST("/api/v1/auth/token/reissue")
    suspend fun reissueToken(
        @Header("Refresh-Token") refreshToken: String,
    ): BaseResponse<AuthToken>
}
