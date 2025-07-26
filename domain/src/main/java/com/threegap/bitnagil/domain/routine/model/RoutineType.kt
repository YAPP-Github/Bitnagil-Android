package com.threegap.bitnagil.domain.routine.model

enum class RoutineType {
    ROUTINE,
    SUB_ROUTINE,
    CHANGED_ROUTINE,
    CHANGED_SUB_ROUTINE,
    ;

    companion object {
        fun fromString(value: String): RoutineType =
            when (value) {
                "ROUTINE" -> ROUTINE
                "SUB_ROUTINE" -> SUB_ROUTINE
                "CHANGED_ROUTINE" -> CHANGED_ROUTINE
                "CHANGED_SUB_ROUTINE" -> CHANGED_SUB_ROUTINE
                else -> throw IllegalArgumentException("Unknown value: $value")
            }
    }
}
