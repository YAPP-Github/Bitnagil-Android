package com.threegap.bitnagil.domain.routine.model

import kotlinx.serialization.Serializable

@Serializable
enum class RecommendedRoutineType {
    PERSONALIZED,
    OUTING,
    WAKE_UP,
    CONNECT,
    REST,
    GROW,
    OUTING_REPORT,
    ;
}
