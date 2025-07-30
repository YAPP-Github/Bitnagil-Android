package com.threegap.bitnagil.domain.user.repository

import com.threegap.bitnagil.domain.user.model.UserProfile

interface UserRepository {
    suspend fun fetchUserProfile(): Result<UserProfile>
}
