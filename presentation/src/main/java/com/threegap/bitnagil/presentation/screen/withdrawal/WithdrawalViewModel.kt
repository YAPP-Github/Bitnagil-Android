package com.threegap.bitnagil.presentation.screen.withdrawal

import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.auth.usecase.WithdrawalUseCase
import com.threegap.bitnagil.presentation.screen.withdrawal.contract.WithdrawalSideEffect
import com.threegap.bitnagil.presentation.screen.withdrawal.contract.WithdrawalState
import com.threegap.bitnagil.presentation.screen.withdrawal.model.WithdrawalReason
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val withdrawalUseCase: WithdrawalUseCase,
) : ContainerHost<WithdrawalState, WithdrawalSideEffect>, ViewModel() {

    override val container: Container<WithdrawalState, WithdrawalSideEffect> = container(initialState = WithdrawalState.Companion.INIT)

    fun onTermsToggle() {
        intent {
            reduce { state.copy(isTermsChecked = !state.isTermsChecked) }
        }
    }

    fun updateCustomReason(text: String) {
        intent {
            reduce { state.copy(customReasonText = text) }
        }
    }

    fun updateSelectedReason(reason: WithdrawalReason?) {
        intent {
            reduce { state.copy(selectedReason = reason, customReasonText = "") }
        }
    }

    fun withdrawal() {
        intent {
            if (state.isLoading) return@intent
            reduce { state.copy(isLoading = true) }
            val reason = state.finalWithdrawalReason
            withdrawalUseCase(reason).fold(
                onSuccess = {
                    reduce { state.copy(isLoading = false, showSuccessDialog = true) }
                },
                onFailure = {
                    reduce { state.copy(isLoading = false) }
                },
            )
        }
    }

    fun navigateToBack() {
        intent {
            postSideEffect(WithdrawalSideEffect.NavigateToBack)
        }
    }

    fun navigateToLogin() {
        intent {
            postSideEffect(WithdrawalSideEffect.NavigateToLogin)
        }
    }
}
