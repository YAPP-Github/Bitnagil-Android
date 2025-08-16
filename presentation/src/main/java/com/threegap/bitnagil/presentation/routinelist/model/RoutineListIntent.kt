package com.threegap.bitnagil.presentation.routinelist.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import java.time.LocalDate

sealed class RoutineListIntent : MviIntent {
    data class UpdateLoading(val isLoading: Boolean) : RoutineListIntent()
    data class LoadRoutines(val routines: RoutinesUiModel) : RoutineListIntent()
    data class OnDateSelect(val date: LocalDate) : RoutineListIntent()
    data class ShowDeleteConfirmBottomSheet(val routine: RoutineUiModel) : RoutineListIntent()
    data object HideDeleteConfirmBottomSheet : RoutineListIntent()
    data object NavigateToBack : RoutineListIntent()
}
