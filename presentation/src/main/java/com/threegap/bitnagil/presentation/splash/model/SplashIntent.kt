package com.threegap.bitnagil.presentation.splash.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class SplashIntent : MviIntent {
    data class SetLoading(val isLoading: Boolean) : SplashIntent()
    data object NavigateToIntro : SplashIntent()
    data object NavigateToHome : SplashIntent()
}
