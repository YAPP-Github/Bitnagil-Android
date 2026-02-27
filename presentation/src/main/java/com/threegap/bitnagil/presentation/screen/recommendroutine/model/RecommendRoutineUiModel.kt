package com.threegap.bitnagil.presentation.screen.recommendroutine.model

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine

data class RecommendRoutineUiModel(
    val id: Long,
    val name: String,
    val level: RecommendLevel?,
    val category: RecommendCategory?,
    val subRoutines: List<RecommendSubRoutineUiModel>,
)

internal fun RecommendRoutine.toUiModel(): RecommendRoutineUiModel =
    RecommendRoutineUiModel(
        id = this.id,
        name = this.name,
        level = this.level,
        category = this.category,
        subRoutines = this.subRoutines.map { it.toUiModel() },
    )
