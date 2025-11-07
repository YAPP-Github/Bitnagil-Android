package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import java.time.LocalDate

data class HomeState(
    val isLoading: Boolean,
    val userNickname: String,
    val todayEmotion: TodayEmotionUiModel?,
    val selectedDate: LocalDate,
    val currentWeeks: List<LocalDate>,
    val routineSchedule: RoutineScheduleUiModel,
) {
    val selectedDateRoutines: List<RoutineUiModel>
        get() = routineSchedule.dailyRoutines[selectedDate.toString()]?.routines ?: emptyList()

    companion object {
        val INIT = HomeState(
            isLoading = false,
            userNickname = "",
            todayEmotion = null,
            selectedDate = LocalDate.now(),
            currentWeeks = LocalDate.now().getCurrentWeekDays(),
            routineSchedule = RoutineScheduleUiModel(),
        )
    }
}
