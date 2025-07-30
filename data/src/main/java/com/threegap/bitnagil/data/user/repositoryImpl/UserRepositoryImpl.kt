package com.threegap.bitnagil.data.user.repositoryImpl

import com.threegap.bitnagil.data.user.datasource.UserDataSource
import com.threegap.bitnagil.data.user.model.response.toDomain
import com.threegap.bitnagil.domain.user.model.UserProfile
import com.threegap.bitnagil.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun fetchUserProfile(): Result<UserProfile> =
        userDataSource.fetchUserProfile().map { it.toDomain() }
}
