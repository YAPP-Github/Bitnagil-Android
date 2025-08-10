package com.threegap.bitnagil.presentation.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.auth.model.UserRole
import com.threegap.bitnagil.domain.auth.usecase.AutoLoginUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.splash.model.SplashIntent
import com.threegap.bitnagil.presentation.splash.model.SplashSideEffect
import com.threegap.bitnagil.presentation.splash.model.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val autoLoginUseCase: AutoLoginUseCase,
) : MviViewModel<SplashState, SplashSideEffect, SplashIntent>(
    initState = SplashState(),
    savedStateHandle = savedStateHandle,
) {

    init {
        performAutoLogin()
    }

    override suspend fun SimpleSyntax<SplashState, SplashSideEffect>.reduceState(
        intent: SplashIntent,
        state: SplashState,
    ): SplashState? =
        when (intent) {
            is SplashIntent.SetUserRole -> {
                state.copy(
                    userRole = intent.userRole,
                    isAutoLoginCompleted = true,
                )
            }

            is SplashIntent.NavigateToLogin -> {
                sendSideEffect(SplashSideEffect.NavigateToLogin)
                null
            }

            is SplashIntent.NavigateToHome -> {
                sendSideEffect(SplashSideEffect.NavigateToHome)
                null
            }

            is SplashIntent.NavigateToTermsAgreement -> {
                sendSideEffect(SplashSideEffect.NavigateToTermsAgreement)
                null
            }

            is SplashIntent.NavigateToOnboarding -> {
                sendSideEffect(SplashSideEffect.NavigateToOnboarding)
                null
            }
        }

    private fun performAutoLogin() {
        viewModelScope.launch {
            try {
                val userRole = withTimeoutOrNull(5000) {
                    autoLoginUseCase()
                }
                sendIntent(SplashIntent.SetUserRole(userRole))
            } catch (e: Exception) {
                sendIntent(SplashIntent.SetUserRole(null))
            }
        }
    }

    fun onAnimationCompleted() {
        val splashState = container.stateFlow.value
        if (!splashState.isAutoLoginCompleted) {
            viewModelScope.launch {
                delay(100)
                onAnimationCompleted()
            }
            return
        }

        when (splashState.userRole) {
            UserRole.GUEST -> sendIntent(SplashIntent.NavigateToTermsAgreement)
            UserRole.USER -> sendIntent(SplashIntent.NavigateToHome)
            UserRole.ONBOARDING -> sendIntent(SplashIntent.NavigateToOnboarding)
            else -> sendIntent(SplashIntent.NavigateToLogin)
        }
    }
}
