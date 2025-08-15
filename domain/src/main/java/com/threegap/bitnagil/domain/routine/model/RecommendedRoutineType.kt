package com.threegap.bitnagil.domain.routine.model

enum class RecommendedRoutineType {
    PERSONALIZED,
    OUTING,
    WAKE_UP,
    CONNECT,
    REST,
    GROW,
    OUTING_REPORT,
    UNKNOWN,
    ;

    companion object {
        fun fromString(categoryName: String?): RecommendedRoutineType =
            RecommendedRoutineType.entries.find { it.name == categoryName } ?: UNKNOWN
    }
}
