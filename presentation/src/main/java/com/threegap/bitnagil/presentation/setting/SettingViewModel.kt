package com.threegap.bitnagil.presentation.setting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.auth.usecase.LogoutUseCase
import com.threegap.bitnagil.domain.auth.usecase.WithdrawalUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.setting.model.mvi.SettingIntent
import com.threegap.bitnagil.presentation.setting.model.mvi.SettingSideEffect
import com.threegap.bitnagil.presentation.setting.model.mvi.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val logoutUseCase: LogoutUseCase,
    private val withdrawalUseCase: WithdrawalUseCase,
) : MviViewModel<SettingState, SettingSideEffect, SettingIntent>(
    initState = SettingState.Init,
    savedStateHandle = savedStateHandle,
) {
    private var setServiceAlarmJob: Job? = null
    private var setPushAlarmJob: Job? = null

    override suspend fun SimpleSyntax<SettingState, SettingSideEffect>.reduceState(
        intent: SettingIntent,
        state: SettingState,
    ): SettingState? {
        when (intent) {
            is SettingIntent.LoadSettingSuccess -> {
                return state.copy(
                    useServiceAlarm = intent.useServiceAlarm,
                    usePushAlarm = intent.usePushAlarm,
                    version = intent.version,
                    latestVersion = intent.latestVersion,
                )
            }
            SettingIntent.TogglePushAlarm -> {
                return state.copy(
                    usePushAlarm = !state.usePushAlarm,
                )
            }
            SettingIntent.ToggleServiceAlarm -> {
                return state.copy(
                    useServiceAlarm = !state.useServiceAlarm,
                )
            }
            SettingIntent.LogoutSuccess -> {
                sendSideEffect(SettingSideEffect.NavigateToIntro)
                return null
            }
            SettingIntent.LogoutLoading -> {
                return state.copy(loading = true)
            }
            SettingIntent.LogoutFailure -> {
                return state.copy(loading = false)
            }
            SettingIntent.WithdrawalSuccess -> {
                sendSideEffect(SettingSideEffect.NavigateToIntro)
                return null
            }
            SettingIntent.WithdrawalLoading -> {
                return state.copy(loading = true)
            }
            SettingIntent.WithdrawalFailure -> {
                return state.copy(loading = false)
            }
        }
    }

    fun toggleServiceAlarm() {
        sendIntent(SettingIntent.ToggleServiceAlarm)
        setServiceAlarmJob?.cancel()
        setServiceAlarmJob = viewModelScope.launch {
            delay(1000L)
        }
    }

    fun togglePushAlarm() {
        sendIntent(SettingIntent.TogglePushAlarm)
        setPushAlarmJob?.cancel()
        setPushAlarmJob = viewModelScope.launch {
            delay(1000L)
        }
    }

    fun logout() {
        viewModelScope.launch {
            sendIntent(SettingIntent.LogoutLoading)
            logoutUseCase().onSuccess {
                sendIntent(SettingIntent.LogoutSuccess)
            }.onFailure {
                sendIntent(SettingIntent.LogoutFailure)
            }
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            sendIntent(SettingIntent.WithdrawalLoading)
            withdrawalUseCase().onSuccess {
                sendIntent(SettingIntent.WithdrawalSuccess)
            }.onFailure {
                sendIntent(SettingIntent.WithdrawalFailure)
            }
        }
    }
}
