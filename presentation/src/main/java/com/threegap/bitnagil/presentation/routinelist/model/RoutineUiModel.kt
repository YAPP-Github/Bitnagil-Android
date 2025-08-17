package com.threegap.bitnagil.presentation.routinelist.model

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.RecommendedRoutineType
import com.threegap.bitnagil.domain.routine.model.Routine
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutineUiModel(
    val routineId: String,
    val routineName: String,
    val repeatDay: List<DayOfWeek>,
    val executionTime: String,
    val routineDate: String,
    val subRoutineNames: List<String>,
    val recommendedRoutineType: RecommendedRoutineType?,
) : Parcelable

fun Routine.toUiModel(): RoutineUiModel =
    RoutineUiModel(
        routineId = this.routineId,
        routineName = this.routineName,
        repeatDay = this.repeatDay,
        executionTime = this.formattedExecutionTime,
        routineDate = this.routineDate,
        subRoutineNames = this.subRoutineNames,
        recommendedRoutineType = this.recommendedRoutineType,
    )

fun RecommendedRoutineType.getIcon(): Int =
    when (this) {
        RecommendedRoutineType.OUTING -> R.drawable.ic_outside
        RecommendedRoutineType.WAKE_UP -> R.drawable.ic_wakeup
        RecommendedRoutineType.CONNECT -> R.drawable.ic_connect
        RecommendedRoutineType.REST -> R.drawable.ic_rest
        RecommendedRoutineType.GROW -> R.drawable.ic_grow
        RecommendedRoutineType.PERSONALIZED -> R.drawable.ic_shine
        RecommendedRoutineType.OUTING_REPORT -> R.drawable.ic_shine
    }

@Composable
fun RecommendedRoutineType.getColor(): Color =
    when (this) {
        RecommendedRoutineType.OUTING -> BitnagilTheme.colors.skyBlue10
        RecommendedRoutineType.WAKE_UP -> BitnagilTheme.colors.orange25
        RecommendedRoutineType.CONNECT -> BitnagilTheme.colors.purple10
        RecommendedRoutineType.REST -> BitnagilTheme.colors.green10
        RecommendedRoutineType.GROW -> BitnagilTheme.colors.pink10
        RecommendedRoutineType.PERSONALIZED -> BitnagilTheme.colors.yellow10
        RecommendedRoutineType.OUTING_REPORT -> BitnagilTheme.colors.yellow10
    }
