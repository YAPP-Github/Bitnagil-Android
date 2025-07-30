package com.threegap.bitnagil.data.user.datasource

import com.threegap.bitnagil.data.user.model.response.UserProfileResponseDto

interface UserDataSource {
    suspend fun fetchUserProfile(): Result<UserProfileResponseDto>
}
