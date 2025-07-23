package com.threegap.bitnagil.presentation.writeroutine.model

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
        fun fromRepeatDay(repeatDay: RepeatDay): Day {
            return when (repeatDay) {
                RepeatDay.MON -> MON
                RepeatDay.TUE -> TUE
                RepeatDay.WED -> WED
                RepeatDay.THU -> THU
                RepeatDay.FRI -> FRI
                RepeatDay.SAT -> SAT
                RepeatDay.SUN -> SUN
            }
        }
    }
}
