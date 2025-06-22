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

    /**
 * Emits a side effect within the Orbit MVI syntax context.
 *
 * @param sideEffect The side effect to be posted.
 */
protected suspend fun SimpleSyntax<STATE, SIDE_EFFECT>.sendSideEffect(sideEffect: SIDE_EFFECT) = postSideEffect(sideEffect)

    /**
     * Computes a new state in response to the given intent and current state.
     *
     * Subclasses must implement this to define how each intent transforms the state.
     *
     * @param intent The intent representing a user or system action.
     * @param state The current UI state.
     * @return The new state if a state change should occur, or null to leave the state unchanged.
     */
    protected abstract suspend fun SimpleSyntax<STATE, SIDE_EFFECT>.reduceState(
        intent: INTENT,
        state: STATE,
    ): STATE?

    /**
         * Processes the given intent by invoking state reduction and updating the state if a new state is produced.
         *
         * If the `reduceState` function returns a non-null state, the container's state is updated accordingly.
         *
         * @param intent The intent to be handled.
         */
        fun sendIntent(intent: INTENT) =
        intent {
            val newState = reduceState(intent, state)

            newState?.let {
                reduce { newState }
            }
        }
}
