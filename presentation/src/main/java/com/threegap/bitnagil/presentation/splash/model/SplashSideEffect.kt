package com.threegap.bitnagil.presentation.splash.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface SplashSideEffect : MviSideEffect {
    data object NavigateToIntro : SplashSideEffect
    data object NavigateToHome : SplashSideEffect
}
