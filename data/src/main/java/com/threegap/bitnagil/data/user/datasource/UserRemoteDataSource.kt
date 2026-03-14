package com.threegap.bitnagil.data.user.datasource

import com.threegap.bitnagil.data.user.model.response.UserProfileResponse

interface UserRemoteDataSource {
    suspend fun fetchUserProfile(): Result<UserProfileResponse>
}
