package com.threegap.bitnagil.presentation.routinelist.contract

import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import com.threegap.bitnagil.presentation.routinelist.model.RoutineUiModel
import com.threegap.bitnagil.presentation.routinelist.model.RoutinesUiModel
import java.time.LocalDate

data class RoutineListState(
    val isLoading: Boolean,
    val routines: RoutinesUiModel,
    val selectedRoutine: RoutineUiModel?,
    val selectedDate: LocalDate,
    val deleteConfirmBottomSheetVisible: Boolean,
    val editConfirmBottomSheetVisible: Boolean,
) {
    val currentWeekDates: List<LocalDate>
        get() = selectedDate.getCurrentWeekDays()

    val selectedDateRoutines: List<RoutineUiModel>
        get() = routines.routines[selectedDate.toString()]?.routineList ?: emptyList()

    companion object {
        val INIT = RoutineListState(
            isLoading = false,
            routines = RoutinesUiModel(),
            selectedRoutine = null,
            selectedDate = LocalDate.now(),
            deleteConfirmBottomSheetVisible = false,
            editConfirmBottomSheetVisible = false,
        )
    }
}
