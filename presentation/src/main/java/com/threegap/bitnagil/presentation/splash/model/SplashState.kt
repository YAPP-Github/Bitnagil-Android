package com.threegap.bitnagil.presentation.splash.model

import com.threegap.bitnagil.domain.auth.model.UserRole
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashState(
    val userRole: UserRole? = null,
    val isAutoLoginCompleted: Boolean = false,
    val isForceUpdateCheckCompleted: Boolean = false,
    val forceUpdateRequired: Boolean = false,
) : MviState
