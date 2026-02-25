package com.threegap.bitnagil.presentation.routinelist.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.presentation.routinelist.contract.RoutineListState
import java.time.LocalDate

internal class RoutineListStatePreviewProvider : PreviewParameterProvider<RoutineListState> {
    override val values = sequenceOf(
        emptyRoutineState,
        withRoutinesState,
    )
}

private val today = LocalDate.now()

private val sampleRoutines = listOf(
    RoutineUiModel(
        routineId = "1",
        routineName = "아침 스트레칭",
        repeatDay = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
        executionTime = "07:00:00",
        routineDate = today.toString(),
        startDate = "2025-01-01",
        endDate = "2025-12-31",
        routineDeletedYn = false,
        subRoutineNames = listOf("목 스트레칭", "어깨 스트레칭"),
        recommendedRoutineType = null,
    ),
    RoutineUiModel(
        routineId = "2",
        routineName = "독서",
        repeatDay = emptyList(),
        executionTime = "09:00:00",
        routineDate = today.toString(),
        startDate = "2025-01-01",
        endDate = "2025-03-31",
        routineDeletedYn = false,
        subRoutineNames = emptyList(),
        recommendedRoutineType = null,
    ),
)

private val emptyRoutineState = RoutineListState.INIT

private val withRoutinesState = RoutineListState(
    isLoading = false,
    selectedDate = today,
    routines = RoutineScheduleUiModel(
        routines = mapOf(
            today.toString() to DailyRoutinesUiModel(routines = sampleRoutines),
        ),
    ),
    selectedRoutine = null,
    deleteConfirmBottomSheetVisible = false,
    editConfirmBottomSheetVisible = false,
)
