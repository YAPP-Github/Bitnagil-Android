package com.threegap.bitnagil.presentation.screen.splash.contract

sealed interface SplashSideEffect {
    data object NavigateToLogin : SplashSideEffect
    data object NavigateToHome : SplashSideEffect
    data object NavigateToTermsAgreement : SplashSideEffect
    data object NavigateToOnboarding : SplashSideEffect
}
