package com.threegap.bitnagil.presentation.routinelist.contract

import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import com.threegap.bitnagil.presentation.routinelist.model.RoutineScheduleUiModel
import com.threegap.bitnagil.presentation.routinelist.model.RoutineUiModel
import java.time.LocalDate

data class RoutineListState(
    val isLoading: Boolean,
    val routines: RoutineScheduleUiModel,
    val selectedRoutine: RoutineUiModel?,
    val selectedDate: LocalDate,
    val deleteConfirmBottomSheetVisible: Boolean,
    val editConfirmBottomSheetVisible: Boolean,
) {
    val currentWeekDates: List<LocalDate>
        get() = selectedDate.getCurrentWeekDays()

    val selectedDateRoutines: List<RoutineUiModel>
        get() = routines.routines[selectedDate.toString()]?.routines ?: emptyList()

    companion object {
        val INIT = RoutineListState(
            isLoading = false,
            routines = RoutineScheduleUiModel.INIT,
            selectedRoutine = null,
            selectedDate = LocalDate.now(),
            deleteConfirmBottomSheetVisible = false,
            editConfirmBottomSheetVisible = false,
        )
    }
}
