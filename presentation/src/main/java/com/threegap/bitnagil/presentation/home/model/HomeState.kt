package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class HomeState(
    val isLoading: Boolean = false,
    val userNickname: String = "",
    val myEmotion: EmotionBallType? = null,
    val todayEmotion: TodayEmotionUiModel? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val currentWeeks: List<LocalDate> = LocalDate.now().getCurrentWeekDays(),
    val routines: RoutinesUiModel = RoutinesUiModel(),
    val routineDetailsBottomSheetVisible: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val selectedRoutine: RoutineUiModel? = null,
    val deletingRoutine: RoutineUiModel? = null,
) : MviState {
    val selectedDateRoutines: List<RoutineUiModel>
        get() = routines.routinesByDate[selectedDate.toString()] ?: emptyList()
}
