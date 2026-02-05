package com.threegap.bitnagil.presentation.common.extension

import com.threegap.bitnagil.domain.routine.model.DayOfWeek

fun DayOfWeek.displayTitle(): String =
    when (this) {
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        DayOfWeek.SUNDAY -> "일"
    }

fun List<DayOfWeek>.displayTitle(): String {
    if (this.isEmpty()) return "x"
    return this.sortedBy { it.ordinal }
        .joinToString(", ") { it.displayTitle() }
}
