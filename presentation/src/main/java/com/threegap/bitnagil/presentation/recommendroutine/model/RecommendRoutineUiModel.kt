package com.threegap.bitnagil.presentation.recommendroutine.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendRoutineUiModel(
    val id: Int = 0,
    val name: String = "",
    val level: RecommendLevel? = null,
    val recommendedRoutineType: RecommendCategory? = null,
    val recommendSubRoutines: List<RecommendSubRoutineUiModel> = emptyList(),
) : Parcelable

fun RecommendRoutine.toUiModel() =
    RecommendRoutineUiModel(
        id = this.id,
        name = this.name,
        level = this.level,
        recommendedRoutineType = this.recommendedRoutineType,
        recommendSubRoutines = this.recommendSubRoutines.map { it.toUiModel() },
    )
