package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class HomeState(
    val isLoading: Boolean = false,
    val userNickname: String = "",
    val selectedDate: LocalDate = LocalDate.now(),
    val currentWeeks: List<LocalDate> = LocalDate.now().getCurrentWeekDays(),
    val routines: RoutinesUiModel = RoutinesUiModel(),
    val currentSortType: RoutineSortType = RoutineSortType.TIME_ORDER,
    val routineSortBottomSheetVisible: Boolean = false,
    val routineDetailsBottomSheetVisible: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val selectedRoutine: RoutineUiModel? = null,
    val deletingRoutine: RoutineUiModel? = null,
) : MviState {
    val selectedDateRoutines: List<RoutineUiModel>
        get() {
            val routines = routines.routinesByDate[selectedDate.toString()] ?: emptyList()
            return when (currentSortType) {
                RoutineSortType.TIME_ORDER -> routines.sortedBy { it.executionTime }
                RoutineSortType.COMPLETED_FIRST -> routines.sortedByDescending { it.isCompleted }
                RoutineSortType.INCOMPLETE_FIRST -> routines.sortedBy { it.isCompleted }
            }
        }
}
