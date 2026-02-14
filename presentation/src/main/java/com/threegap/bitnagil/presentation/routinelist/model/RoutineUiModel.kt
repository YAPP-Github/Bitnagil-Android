package com.threegap.bitnagil.presentation.routinelist.model

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.presentation.home.util.formatExecutionTime12Hour
import com.threegap.bitnagil.presentation.home.util.toShortDateFormat

data class RoutineUiModel(
    val routineId: String,
    val routineName: String,
    val repeatDay: List<DayOfWeek>,
    val executionTime: String,
    val routineDate: String,
    val startDate: String,
    val endDate: String,
    val routineDeletedYn: Boolean,
    val subRoutineNames: List<String>,
    val recommendedRoutineType: RecommendCategory?,
) {
    val formattedDateRange: String
        get() = "${startDate.toShortDateFormat()} - ${endDate.toShortDateFormat()}"
}

internal fun Routine.toUiModel(): RoutineUiModel =
    RoutineUiModel(
        routineId = this.id,
        routineName = this.name,
        repeatDay = this.repeatDays,
        executionTime = this.executionTime.formatExecutionTime12Hour(),
        routineDate = this.routineDate,
        startDate = this.startDate,
        endDate = this.endDate,
        routineDeletedYn = this.isDeleted,
        subRoutineNames = this.subRoutineNames,
        recommendedRoutineType = this.recommendedRoutineType,
    )
