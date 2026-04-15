package com.threegap.bitnagil.presentation.model.date

import com.threegap.bitnagil.domain.routine.model.DayOfWeek

val DayOfWeek.displayTitle: String
    get() = when (this) {
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        DayOfWeek.SUNDAY -> "일"
    }

val List<DayOfWeek>.displayTitle: String
    get() {
        if (this.isEmpty()) return "x"
        return this.sortedBy { it.ordinal }
            .joinToString(", ") { it.displayTitle }
    }
