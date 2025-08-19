package com.threegap.bitnagil.domain.recommendroutine.model

data class RecommendRoutine(
    val id: Int,
    val name: String,
    val description: String,
    val level: RecommendLevel,
    val executionTime: String,
    val recommendedRoutineType: RecommendCategory,
    val recommendSubRoutines: List<RecommendSubRoutine>,
)
