package com.threegap.bitnagil.presentation.routinelist.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class RoutineListState(
    val selectedDate: LocalDate = LocalDate.now(),
    val deleteConfirmBottomSheetVisible: Boolean = false,
) : MviState
