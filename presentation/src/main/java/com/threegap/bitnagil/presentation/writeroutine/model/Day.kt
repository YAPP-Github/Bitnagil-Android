package com.threegap.bitnagil.presentation.writeroutine.model

import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay

enum class Day(val text: String) {
    MON(text = "월"),
    TUE(text = "화"),
    WED(text = "수"),
    THU(text = "목"),
    FRI(text = "금"),
    SAT(text = "토"),
    SUN(text = "일"),
    ;

    fun toRepeatDay(): RepeatDay {
        return when (this) {
            MON -> RepeatDay.MON
            TUE -> RepeatDay.TUE
            WED -> RepeatDay.WED
            THU -> RepeatDay.THU
            FRI -> RepeatDay.FRI
            SAT -> RepeatDay.SAT
            SUN -> RepeatDay.SUN
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
