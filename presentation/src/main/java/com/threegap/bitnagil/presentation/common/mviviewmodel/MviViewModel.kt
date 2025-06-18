package com.threegap.bitnagil.presentation.common.mviviewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

abstract class MviViewModel<STATE : MviState, SIDE_EFFECT : MviSideEffect, INTENT : MviIntent>(
    private val initState: STATE,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<STATE, SIDE_EFFECT>, ViewModel() {
    override val container: Container<STATE, SIDE_EFFECT>
        get() = container(initialState = initState, savedStateHandle = savedStateHandle)

    val stateFlow: StateFlow<STATE> = container.stateFlow

    val sideEffectFlow: Flow<SIDE_EFFECT> = container.sideEffectFlow

    protected fun sendSideEffect(sideEffect: SIDE_EFFECT) =
        intent {
            postSideEffect(sideEffect)
        }

    protected abstract fun reduceState(intent: INTENT): STATE?

    protected fun sendIntent(intent: INTENT) =
        intent {
            val newState = reduceState(intent)

            newState?.let {
                reduce { newState }
            }
        }
}
