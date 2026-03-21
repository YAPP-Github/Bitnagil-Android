package com.threegap.bitnagil.data.user.service

import com.threegap.bitnagil.data.user.model.response.UserProfileResponse
import retrofit2.http.GET

interface UserService {
    @GET("/api/v1/users/infos")
    suspend fun fetchUserProfile(): Result<UserProfileResponse>
}
