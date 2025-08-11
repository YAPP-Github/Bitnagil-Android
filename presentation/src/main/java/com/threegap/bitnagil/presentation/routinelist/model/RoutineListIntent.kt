package com.threegap.bitnagil.presentation.routinelist.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import java.time.LocalDate

sealed class RoutineListIntent : MviIntent {
    data class OnDateSelect(val date: LocalDate) : RoutineListIntent()
    data object ShowDeleteConfirmBottomSheet : RoutineListIntent()
    data object HideDeleteConfirmBottomSheet : RoutineListIntent()
    data object NavigateToBack : RoutineListIntent()
}
