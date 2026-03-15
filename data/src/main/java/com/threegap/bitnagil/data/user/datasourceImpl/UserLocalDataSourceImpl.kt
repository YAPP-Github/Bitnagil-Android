package com.threegap.bitnagil.data.user.datasourceImpl

import com.threegap.bitnagil.data.user.datasource.UserLocalDataSource
import com.threegap.bitnagil.domain.user.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSourceImpl @Inject constructor() : UserLocalDataSource {
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    override val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    override fun saveUserProfile(userProfile: UserProfile) {
        _userProfile.update { userProfile }
    }

    override fun clearCache() {
        _userProfile.update { null }
    }
}
