package com.threegap.bitnagil.presentation.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.auth.usecase.HasTokenUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.splash.model.SplashIntent
import com.threegap.bitnagil.presentation.splash.model.SplashSideEffect
import com.threegap.bitnagil.presentation.splash.model.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private var hasToken: Boolean? = null

    init {
        checkTokenStatus()
    }

    override suspend fun SimpleSyntax<SplashState, SplashSideEffect>.reduceState(
        intent: SplashIntent,
        state: SplashState,
    ): SplashState? =
        when (intent) {
            is SplashIntent.SetTokenChecked -> {
                state.copy(isTokenChecked = intent.hasToken != null)
            }

            is SplashIntent.NavigateToIntro -> {
                sendSideEffect(SplashSideEffect.NavigateToIntro)
                null
            }

            is SplashIntent.NavigateToHome -> {
                sendSideEffect(SplashSideEffect.NavigateToHome)
                null
            }
        }

    private fun checkTokenStatus() {
        viewModelScope.launch {
            try {
                hasToken = hasTokenUseCase()
                sendIntent(SplashIntent.SetTokenChecked(hasToken))
            } catch (e: Exception) {
                hasToken = false
                sendIntent(SplashIntent.SetTokenChecked(false))
            }
        }
    }

    fun onAnimationCompleted() {
        val tokenResult = hasToken
        if (tokenResult == null) {
            viewModelScope.launch {
                delay(100)
                onAnimationCompleted()
            }
            return
        }

        if (tokenResult) {
            sendIntent(SplashIntent.NavigateToHome)
        } else {
            sendIntent(SplashIntent.NavigateToIntro)
        }
    }
}
