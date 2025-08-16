package com.threegap.bitnagil.presentation.routinelist.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class RoutineListState(
    val isLoading: Boolean = false,
    val routines: RoutinesUiModel = RoutinesUiModel(),
    val selectedRoutine: RoutineUiModel? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val deleteConfirmBottomSheetVisible: Boolean = false,
) : MviState {
    val currentWeekDates: List<LocalDate>
        get() = selectedDate.getCurrentWeekDays()

    val selectedDateRoutines: List<RoutineUiModel>
        get() = routines.routines[selectedDate.toString()]?.routineList ?: emptyList()
}
