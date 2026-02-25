package com.threegap.bitnagil.presentation.screen.home.contract

import com.threegap.bitnagil.presentation.screen.home.model.DailyEmotionUiModel
import com.threegap.bitnagil.presentation.screen.home.model.RoutineScheduleUiModel
import com.threegap.bitnagil.presentation.screen.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.screen.home.util.getCurrentWeekDays
import java.time.LocalDate

data class HomeState(
    val loadingCount: Int,
    val userNickname: String,
    val dailyEmotion: DailyEmotionUiModel,
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
            dailyEmotion = DailyEmotionUiModel.Companion.INIT,
            selectedDate = LocalDate.now(),
            currentWeeks = LocalDate.now().getCurrentWeekDays(),
            routineSchedule = RoutineScheduleUiModel.Companion.INIT,
        )
    }
}
