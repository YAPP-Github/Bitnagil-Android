package com.threegap.bitnagil.data.user.service

import com.threegap.bitnagil.data.user.model.response.UserProfileResponseDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.GET

interface UserService {
    @GET("/api/v1/users/nickname")
    suspend fun fetchUserProfile(): BaseResponse<UserProfileResponseDto>
}
