package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import java.time.LocalDate

sealed class HomeIntent : MviIntent {
    data class UpdateLoading(val isLoading: Boolean) : HomeIntent()
    data class LoadUserProfile(val nickname: String) : HomeIntent()
    data class LoadTodayEmotion(val emotion: TodayEmotionUiModel?) : HomeIntent()
    data class LoadWeeklyRoutines(val routines: RoutineScheduleUiModel) : HomeIntent()
    data class OnDateSelect(val date: LocalDate) : HomeIntent()
    data class ToggleRoutine(val routineId: String) : HomeIntent()
    data class ToggleSubRoutine(val routineId: String, val subRoutineIndex: Int) : HomeIntent()
    data object RoutineToggleCompletionFailure : HomeIntent()
    data object OnHelpClick : HomeIntent()
    data object OnRegisterEmotionClick : HomeIntent()
    data object OnRegisterRoutineClick : HomeIntent()
    data object OnPreviousWeekClick : HomeIntent()
    data object OnNextWeekClick : HomeIntent()
    data object OnShowMoreRoutinesClick : HomeIntent()
}
