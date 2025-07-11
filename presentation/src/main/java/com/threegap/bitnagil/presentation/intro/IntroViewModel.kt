package com.threegap.bitnagil.presentation.intro

import androidx.lifecycle.SavedStateHandle
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.intro.model.IntroIntent
import com.threegap.bitnagil.presentation.intro.model.IntroSideEffect
import com.threegap.bitnagil.presentation.intro.model.IntroState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : MviViewModel<IntroState, IntroSideEffect, IntroIntent>(
    initState = IntroState(),
    savedStateHandle = savedStateHandle,
) {
    override suspend fun SimpleSyntax<IntroState, IntroSideEffect>.reduceState(
        intent: IntroIntent,
        state: IntroState,
    ): IntroState? =
        when (intent) {
            is IntroIntent.OnStartButtonClick -> {
                sendSideEffect(IntroSideEffect.NavigateToLogin)
                state.copy(isLoading = false)
            }
        }
}
