package com.threegap.bitnagil.domain.recommendroutine.model

import kotlinx.serialization.Serializable

@Serializable
enum class EmotionMarbleType {
    CALM,
    VITALITY,
    LETHARGY,
    ANXIETY,
    SATISFACTION,
    FATIGUE,
}
