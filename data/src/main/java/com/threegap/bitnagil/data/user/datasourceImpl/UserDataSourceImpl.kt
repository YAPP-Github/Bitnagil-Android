package com.threegap.bitnagil.data.user.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.user.datasource.UserDataSource
import com.threegap.bitnagil.data.user.model.response.UserProfileResponse
import com.threegap.bitnagil.data.user.service.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserDataSource {
    override suspend fun fetchUserProfile(): Result<UserProfileResponse> =
        safeApiCall {
            userService.fetchUserProfile()
        }
}
