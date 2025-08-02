package com.threegap.bitnagil.domain.routine.model

enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY,
    ;

    private fun toKoreanShort(): String =
        when (this) {
            MONDAY -> "월"
            TUESDAY -> "화"
            WEDNESDAY -> "수"
            THURSDAY -> "목"
            FRIDAY -> "금"
            SATURDAY -> "토"
            SUNDAY -> "일"
        }

    companion object {
        fun fromString(value: String): DayOfWeek =
            when (value) {
                "MONDAY" -> MONDAY
                "TUESDAY" -> TUESDAY
                "WEDNESDAY" -> WEDNESDAY
                "THURSDAY" -> THURSDAY
                "FRIDAY" -> FRIDAY
                "SATURDAY" -> SATURDAY
                "SUNDAY" -> SUNDAY
                else -> throw IllegalArgumentException("Unknown value: $value")
            }

        fun List<DayOfWeek>.formatRepeatDays(): String {
            if (this.isEmpty()) return "반복 없음"
            return this.sortedBy { it.ordinal }
                .joinToString(", ") { it.toKoreanShort() }
        }
    }
}
