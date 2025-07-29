package com.threegap.bitnagil.data.user.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.user.datasource.UserDataSource
import com.threegap.bitnagil.data.user.model.response.UserProfileResponseDto
import com.threegap.bitnagil.data.user.service.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserDataSource {
    override suspend fun fetchUserProfile(): Result<UserProfileResponseDto> =
        safeApiCall {
            userService.fetchUserProfile()
        }
}
