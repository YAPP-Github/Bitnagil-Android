package com.threegap.bitnagil.data.user.datasource

import com.threegap.bitnagil.data.user.model.response.UserProfileResponse

interface UserDataSource {
    suspend fun fetchUserProfile(): Result<UserProfileResponse>
}
