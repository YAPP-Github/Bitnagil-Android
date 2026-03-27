package com.threegap.bitnagil.presentation.screen.home.contract

import com.threegap.bitnagil.presentation.screen.home.model.DailyEmotionUiModel
import com.threegap.bitnagil.presentation.screen.home.model.RoutineScheduleUiModel
import com.threegap.bitnagil.presentation.screen.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.screen.home.util.getCurrentWeekDays
import java.time.LocalDate

data class HomeState(
    val userNickname: String,
    val dailyEmotion: DailyEmotionUiModel,
    val selectedDate: LocalDate,
    val currentWeeks: List<LocalDate>,
    val routineSchedule: RoutineScheduleUiModel,
    val showSyncErrorDialog: Boolean,
) {
    val selectedDateRoutines: List<RoutineUiModel>
        get() = routineSchedule.dailyRoutines[selectedDate.toString()]?.routines ?: emptyList()

    companion object {
        val INIT = HomeState(
            userNickname = "",
            dailyEmotion = DailyEmotionUiModel.INIT,
            selectedDate = LocalDate.now(),
            currentWeeks = LocalDate.now().getCurrentWeekDays(),
            routineSchedule = RoutineScheduleUiModel.INIT,
            showSyncErrorDialog = false,
        )
    }
}
