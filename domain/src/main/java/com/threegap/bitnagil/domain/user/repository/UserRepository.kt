package com.threegap.bitnagil.domain.user.repository

import com.threegap.bitnagil.domain.user.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUserProfile(): Flow<Result<UserProfile>>
    fun clearCache()
}
