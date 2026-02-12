package com.threegap.bitnagil.presentation.setting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.auth.usecase.LogoutUseCase
import com.threegap.bitnagil.presentation.common.version.VersionNameProvider
import com.threegap.bitnagil.presentation.setting.contract.SettingSideEffect
import com.threegap.bitnagil.presentation.setting.contract.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val logoutUseCase: LogoutUseCase,
    versionNameProvider: VersionNameProvider,
) : ContainerHost<SettingState, SettingSideEffect>, ViewModel() {
    private var setServiceAlarmJob: Job? = null
    private var setPushAlarmJob: Job? = null

    override val container: Container<SettingState, SettingSideEffect> = container(
        initialState = SettingState.Init.copy(version = versionNameProvider.getVersionName()),
        savedStateHandle = savedStateHandle,
    )

    fun showLogoutDialog() = intent {
        reduce {
            state.copy(logoutConfirmDialogVisible = true)
        }
    }

    fun hideConfirmDialog() = intent {
        reduce {
            state.copy(logoutConfirmDialogVisible = false)
        }
    }

    fun logout() = intent {
        if (container.stateFlow.value.loading) return@intent

        reduce {
            state.copy(
                logoutConfirmDialogVisible = false,
                loading = true,
            )
        }

        viewModelScope.launch {
            logoutUseCase().fold(
                onSuccess = {
                    postSideEffect(SettingSideEffect.NavigateToLogin)
                },
                onFailure = {
                    reduce {
                        state.copy(loading = false)
                    }
                },
            )
        }
    }

    fun toggleServiceAlarm() = intent {
        reduce {
            state.copy(useServiceAlarm = !state.useServiceAlarm)
        }

        setServiceAlarmJob?.cancel()
        setServiceAlarmJob = viewModelScope.launch {
            delay(1000L)
        }
    }

    fun togglePushAlarm() = intent {
        reduce {
            state.copy(usePushAlarm = !state.usePushAlarm)
        }

        setPushAlarmJob?.cancel()
        setPushAlarmJob = viewModelScope.launch {
            delay(1000L)
        }
    }

    fun navigateToWithdrawal() = intent {
        postSideEffect(SettingSideEffect.NavigateToWithdrawal)
    }
}
