package com.threegap.bitnagil.presentation.recommendroutine.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendRoutineUiModel(
    val id: Int,
    val name: String,
    val description: String,
    val level: RecommendLevel,
    val executionTime: String,
    val recommendSubRoutines: List<RecommendSubRoutineUiModel>,
) : Parcelable

fun RecommendRoutine.toUiModel() =
    RecommendRoutineUiModel(
        id = this.id,
        name = this.name,
        description = this.description,
        level = this.level,
        executionTime = this.executionTime,
        recommendSubRoutines = this.recommendSubRoutines.map { it.toUiModel() },
    )
