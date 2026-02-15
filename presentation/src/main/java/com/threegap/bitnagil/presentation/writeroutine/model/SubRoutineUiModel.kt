package com.threegap.bitnagil.presentation.writeroutine.model

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendSubRoutine

data class SubRoutineUiModel(
    val id: String,
    val name: String,
)

internal fun RecommendSubRoutine.toUiModel(): SubRoutineUiModel =
    SubRoutineUiModel(
        id = id.toString(),
        name = name,
    )
