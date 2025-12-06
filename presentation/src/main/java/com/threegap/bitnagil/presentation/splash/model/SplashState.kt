package com.threegap.bitnagil.presentation.splash.model

import com.threegap.bitnagil.domain.auth.model.UserRole

data class SplashState(
    val userRole: UserRole?,
    val isAutoLoginCompleted: Boolean,
    val isForceUpdateCheckCompleted: Boolean,
    val forceUpdateRequired: Boolean,
) {
    companion object {
        val INIT = SplashState(
            userRole = null,
            isAutoLoginCompleted = false,
            isForceUpdateCheckCompleted = false,
            forceUpdateRequired = false,
        )
    }
}
