package com.threegap.bitnagil.data.user.datasource

import com.threegap.bitnagil.domain.user.model.UserProfile
import kotlinx.coroutines.flow.StateFlow

interface UserLocalDataSource {
    val userProfile: StateFlow<UserProfile?>
    fun saveUserProfile(userProfile: UserProfile)
    fun clearCache()
}
