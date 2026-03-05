package com.threegap.bitnagil.presentation.screen.routinewrite.model

import com.threegap.bitnagil.domain.routine.model.DayOfWeek

enum class Day(val text: String) {
    MON(text = "월"),
    TUE(text = "화"),
    WED(text = "수"),
    THU(text = "목"),
    FRI(text = "금"),
    SAT(text = "토"),
    SUN(text = "일"),
    ;

    fun toRepeatDay(): DayOfWeek {
        return when (this) {
            MON -> DayOfWeek.MONDAY
            TUE -> DayOfWeek.TUESDAY
            WED -> DayOfWeek.WEDNESDAY
            THU -> DayOfWeek.THURSDAY
            FRI -> DayOfWeek.FRIDAY
            SAT -> DayOfWeek.SATURDAY
            SUN -> DayOfWeek.SUNDAY
        }
    }

    companion object {
        fun fromDayOfWeek(dayOfWeek: DayOfWeek): Day {
            return when (dayOfWeek) {
                DayOfWeek.MONDAY -> MON
                DayOfWeek.TUESDAY -> TUE
                DayOfWeek.WEDNESDAY -> WED
                DayOfWeek.THURSDAY -> THU
                DayOfWeek.FRIDAY -> FRI
                DayOfWeek.SATURDAY -> SAT
                DayOfWeek.SUNDAY -> SUN
            }
        }
    }
}
