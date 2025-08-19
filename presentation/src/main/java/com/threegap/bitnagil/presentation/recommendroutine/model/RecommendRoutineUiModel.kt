package com.threegap.bitnagil.presentation.recommendroutine.model

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel.LEVEL1
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel.LEVEL2
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel.LEVEL3
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendRoutineUiModel(
    val id: Int = 0,
    val name: String = "",
    val level: RecommendLevel? = null,
    val recommendedRoutineType: RecommendCategory? = null,
    val recommendSubRoutines: List<RecommendSubRoutineUiModel> = emptyList(),
) : Parcelable {
    val icon: Int
        get() = recommendedRoutineType?.getIcon() ?: R.drawable.ic_shine

    val color: Color
        @Composable get() = recommendedRoutineType?.getColor() ?: BitnagilTheme.colors.yellow10
}

fun RecommendRoutine.toUiModel() =
    RecommendRoutineUiModel(
        id = this.id,
        name = this.name,
        level = this.level,
        recommendedRoutineType = this.recommendedRoutineType,
        recommendSubRoutines = this.recommendSubRoutines.map { it.toUiModel() },
    )

private fun RecommendCategory.getIcon(): Int =
    when (this) {
        RecommendCategory.OUTING -> R.drawable.ic_outside
        RecommendCategory.WAKE_UP -> R.drawable.ic_wakeup
        RecommendCategory.CONNECT -> R.drawable.ic_connect
        RecommendCategory.REST -> R.drawable.ic_rest
        RecommendCategory.GROW -> R.drawable.ic_grow
        RecommendCategory.PERSONALIZED -> R.drawable.ic_shine
        RecommendCategory.OUTING_REPORT -> R.drawable.ic_shine
        RecommendCategory.UNKNOWN -> R.drawable.ic_shine
    }

@Composable
private fun RecommendCategory.getColor(): Color =
    when (this) {
        RecommendCategory.OUTING -> BitnagilTheme.colors.skyBlue10
        RecommendCategory.WAKE_UP -> BitnagilTheme.colors.orange25
        RecommendCategory.CONNECT -> BitnagilTheme.colors.purple10
        RecommendCategory.REST -> BitnagilTheme.colors.green10
        RecommendCategory.GROW -> BitnagilTheme.colors.pink10
        RecommendCategory.PERSONALIZED -> BitnagilTheme.colors.yellow10
        RecommendCategory.OUTING_REPORT -> BitnagilTheme.colors.yellow10
        RecommendCategory.UNKNOWN -> BitnagilTheme.colors.yellow10
    }
