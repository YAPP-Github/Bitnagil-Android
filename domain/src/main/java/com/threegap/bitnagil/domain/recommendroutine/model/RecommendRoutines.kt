package com.threegap.bitnagil.domain.recommendroutine.model

import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType

data class RecommendRoutines(
    val recommendRoutinesByCategory: Map<RecommendCategory, List<RecommendRoutine>>,
    val emotionMarbleType: EmotionMarbleType?,
)
