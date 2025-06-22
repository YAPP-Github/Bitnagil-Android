package com.threegap.bitnagil.presentation.common.mviviewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

abstract class MviViewModel<STATE : MviState, SIDE_EFFECT : MviSideEffect, INTENT : MviIntent>(
    initState: STATE,
    savedStateHandle: SavedStateHandle,
) : ContainerHost<STATE, SIDE_EFFECT>, ViewModel() {
    override val container = container<STATE, SIDE_EFFECT>(initialState = initState, savedStateHandle = savedStateHandle)

    val stateFlow: StateFlow<STATE> get() = container.stateFlow
    val sideEffectFlow: Flow<SIDE_EFFECT> get() = container.sideEffectFlow

    suspend fun SimpleSyntax<STATE, SIDE_EFFECT>.sendSideEffect(sideEffect: SIDE_EFFECT) = postSideEffect(sideEffect)

    protected abstract suspend fun SimpleSyntax<STATE, SIDE_EFFECT>.reduceState(
        intent: INTENT,
        state: STATE,
    ): STATE?

    fun sendIntent(intent: INTENT) =
        intent {
            val newState = reduceState(intent, state)

            newState?.let {
                reduce { newState }
            }
        }
}
