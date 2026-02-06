package com.threegap.bitnagil.domain.recommendroutine.model

import kotlinx.serialization.Serializable

@Serializable
enum class RecommendCategory {
    PERSONALIZED,
    OUTING,
    WAKE_UP,
    CONNECT,
    REST,
    GROW,
    OUTING_REPORT,
    UNKNOWN,
    ;
}
