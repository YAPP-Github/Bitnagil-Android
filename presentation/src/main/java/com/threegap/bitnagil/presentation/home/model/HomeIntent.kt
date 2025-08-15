package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import java.time.LocalDate

sealed class HomeIntent : MviIntent {
    data class UpdateLoading(val isLoading: Boolean) : HomeIntent()
    data class LoadUserProfile(val nickname: String) : HomeIntent()
    data class LoadTodayEmotion(val emotion: TodayEmotionUiModel?) : HomeIntent()
    data class LoadWeeklyRoutines(val routines: RoutinesUiModel) : HomeIntent()
    data class OnDateSelect(val date: LocalDate) : HomeIntent()
    data class OnRoutineCompletionToggle(val routineId: String, val isCompleted: Boolean) : HomeIntent()
    data class OnSubRoutineCompletionToggle(val routineId: String, val subRoutineIndex: Int, val isCompleted: Boolean) : HomeIntent()
    data object RoutineToggleCompletionFailure : HomeIntent()
    data object OnRegisterEmotionClick : HomeIntent()
    data object OnRegisterRoutineClick : HomeIntent()
    data object OnPreviousWeekClick : HomeIntent()
    data object OnNextWeekClick : HomeIntent()
}
