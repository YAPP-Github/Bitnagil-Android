package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import java.time.LocalDate

data class HomeState(
    val loadingCount: Int,
    val userNickname: String,
    val todayEmotion: TodayEmotionUiModel?,
    val selectedDate: LocalDate,
    val currentWeeks: List<LocalDate>,
    val routineSchedule: RoutineScheduleUiModel,
) {
    val isLoading: Boolean
        get() = loadingCount > 0

    val selectedDateRoutines: List<RoutineUiModel>
        get() = routineSchedule.dailyRoutines[selectedDate.toString()]?.routines ?: emptyList()

    companion object {
        val INIT = HomeState(
            loadingCount = 0,
            userNickname = "",
            todayEmotion = null,
            selectedDate = LocalDate.now(),
            currentWeeks = LocalDate.now().getCurrentWeekDays(),
            routineSchedule = RoutineScheduleUiModel(),
        )
    }
}
