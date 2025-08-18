package com.threegap.bitnagil.presentation.withdrawal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.auth.usecase.WithdrawalUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.withdrawal.model.WithdrawalIntent
import com.threegap.bitnagil.presentation.withdrawal.model.WithdrawalSideEffect
import com.threegap.bitnagil.presentation.withdrawal.model.WithdrawalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val withdrawalUseCase: WithdrawalUseCase,
) : MviViewModel<WithdrawalState, WithdrawalSideEffect, WithdrawalIntent>(
    savedStateHandle = savedStateHandle,
    initState = WithdrawalState(),
) {
    override suspend fun SimpleSyntax<WithdrawalState, WithdrawalSideEffect>.reduceState(
        intent: WithdrawalIntent,
        state: WithdrawalState,
    ): WithdrawalState? {
        val newState = when (intent) {
            is WithdrawalIntent.UpdateLoading -> state.copy(isLoading = intent.isLoading)
            is WithdrawalIntent.OnTermsToggle -> state.copy(isTermsChecked = !state.isTermsChecked)
            is WithdrawalIntent.ShowSuccessDialog -> state.copy(showSuccessDialog = true)

            is WithdrawalIntent.OnCustomReasonChanged -> {
                state.copy(customReasonText = intent.text)
            }

            is WithdrawalIntent.OnReasonSelected -> {
                state.copy(
                    selectedReason = intent.reason,
                    customReasonText = "",
                )
            }

            is WithdrawalIntent.OnBackClick -> {
                sendSideEffect(WithdrawalSideEffect.NavigateToBack)
                null
            }

            is WithdrawalIntent.OnSuccessDialogConfirm -> {
                sendSideEffect(WithdrawalSideEffect.NavigateToLogin)
                null
            }
        }

        return newState
    }

    fun withdrawal() {
        if (container.stateFlow.value.isLoading) return
        sendIntent(WithdrawalIntent.UpdateLoading(true))
        viewModelScope.launch {
            withdrawalUseCase().fold(
                onSuccess = {
                    sendIntent(WithdrawalIntent.UpdateLoading(false))
                    sendIntent(WithdrawalIntent.ShowSuccessDialog)
                },
                onFailure = {
                    sendIntent(WithdrawalIntent.UpdateLoading(false))
                },
            )
        }
    }
}
