package com.threegap.bitnagil.presentation.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.auth.usecase.HasTokenUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.splash.model.SplashIntent
import com.threegap.bitnagil.presentation.splash.model.SplashSideEffect
import com.threegap.bitnagil.presentation.splash.model.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val hasTokenUseCase: HasTokenUseCase,
) : MviViewModel<SplashState, SplashSideEffect, SplashIntent>(
    initState = SplashState(),
    savedStateHandle = savedStateHandle,
) {

    init {
        checkAutoLogin()
    }

    override suspend fun SimpleSyntax<SplashState, SplashSideEffect>.reduceState(
        intent: SplashIntent,
        state: SplashState,
    ): SplashState? =
        when (intent) {
            is SplashIntent.SetLoading -> {
                state.copy(isLoading = intent.isLoading)
            }

            is SplashIntent.NavigateToIntro -> {
                sendSideEffect(SplashSideEffect.NavigateToIntro)
                state.copy(isLoading = false)
            }

            is SplashIntent.NavigateToHome -> {
                sendSideEffect(SplashSideEffect.NavigateToHome)
                state.copy(isLoading = false)
            }
        }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            sendIntent(SplashIntent.SetLoading(true))
            val delayDeferred = async { delay(2000L) }
            val tokenDeferred = async { hasTokenUseCase() }

            delayDeferred.await()
            val hasToken = tokenDeferred.await()

            if (hasToken) {
                sendIntent(SplashIntent.NavigateToHome)
            } else {
                sendIntent(SplashIntent.NavigateToIntro)
            }
        }
    }
}
