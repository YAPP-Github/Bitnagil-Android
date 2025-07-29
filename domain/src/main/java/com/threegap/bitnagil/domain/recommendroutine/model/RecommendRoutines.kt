package com.threegap.bitnagil.domain.recommendroutine.model

data class RecommendRoutines(
    val recommendRoutinesByCategory: Map<RecommendCategory, List<RecommendRoutine>>,
    val emotionMarbleType: EmotionMarbleType?,
)
