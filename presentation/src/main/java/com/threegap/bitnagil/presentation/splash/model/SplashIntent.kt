package com.threegap.bitnagil.presentation.splash.model

import com.threegap.bitnagil.domain.auth.model.UserRole
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class SplashIntent : MviIntent {
    data class SetUserRole(val userRole: UserRole?) : SplashIntent()
    data object NavigateToLogin : SplashIntent()
    data object NavigateToHome : SplashIntent()
    data object NavigateToTermsAgreement : SplashIntent()
    data object NavigateToOnboarding : SplashIntent()
    data class SetForceUpdateResult(val isRequired: Boolean) : SplashIntent()
}
