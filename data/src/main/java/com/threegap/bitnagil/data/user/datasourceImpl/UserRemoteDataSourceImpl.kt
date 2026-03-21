package com.threegap.bitnagil.data.user.datasourceImpl

import com.threegap.bitnagil.data.user.datasource.UserRemoteDataSource
import com.threegap.bitnagil.data.user.model.response.UserProfileResponse
import com.threegap.bitnagil.data.user.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserRemoteDataSource {
    override suspend fun fetchUserProfile(): Result<UserProfileResponse> =
        userService.fetchUserProfile()
}
