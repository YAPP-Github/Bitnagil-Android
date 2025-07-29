package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import java.time.LocalDate

sealed class HomeIntent : MviIntent {
    data class UpdateLoading(val isLoading: Boolean) : HomeIntent()
    data class LoadUserProfile(val nickname: String) : HomeIntent()
    data class LoadWeeklyRoutines(val routines: RoutinesUiModel) : HomeIntent()
    data class OnDateSelect(val date: LocalDate) : HomeIntent()
    data class OnRoutineCompletionToggle(val routineId: String, val isCompleted: Boolean) : HomeIntent()
    data class OnSubRoutineCompletionToggle(val routineId: String, val subRoutineId: String, val isCompleted: Boolean) : HomeIntent()
    data class OnSortTypeChange(val sortType: RoutineSortType) : HomeIntent()
    data class DeleteRoutineOptimistically(val routineId: String) : HomeIntent()
    data class ConfirmRoutineDeletion(val routineId: String) : HomeIntent()
    data class RestoreRoutinesAfterDeleteFailure(val backupRoutines: RoutinesUiModel) : HomeIntent()
    data class DeleteRoutineByDayOptimistically(val routineId: String, val performedDate: String) : HomeIntent()
    data class ConfirmRoutineByDayDeletion(val routineId: String, val performedDate: String) : HomeIntent()
    data class RestoreRoutinesAfterDeleteByDayFailure(val backupRoutines: RoutinesUiModel) : HomeIntent()
    data class ShowRoutineDetailsBottomSheet(val routine: RoutineUiModel) : HomeIntent()
    data class ShowDeleteConfirmDialog(val routine: RoutineUiModel) : HomeIntent()
    data object OnPreviousWeekClick : HomeIntent()
    data object OnNextWeekClick : HomeIntent()
    data object ShowRoutineSortBottomSheet : HomeIntent()
    data object HideRoutineSortBottomSheet : HomeIntent()
    data object HideRoutineDetailsBottomSheet : HomeIntent()
    data object HideDeleteConfirmDialog : HomeIntent()
}
