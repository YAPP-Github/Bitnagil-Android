package com.threegap.bitnagil.domain.recommendroutine.model

data class RecommendRoutine(
    val id: Long,
    val name: String,
    val description: String,
    val level: RecommendLevel,
    val executionTime: String,
    val category: RecommendCategory,
    val subRoutines: List<RecommendSubRoutine>,
)
