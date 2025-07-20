package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectableDay(
    val day: Day,
    val selected: Boolean,
) : Parcelable {
    companion object {
        val defaultList = listOf(
            SelectableDay(day = Day.MON, selected = false),
            SelectableDay(day = Day.TUE, selected = false),
            SelectableDay(day = Day.WED, selected = false),
            SelectableDay(day = Day.THU, selected = false),
            SelectableDay(day = Day.FRI, selected = false),
            SelectableDay(day = Day.SAT, selected = false),
            SelectableDay(day = Day.SUN, selected = false),
        )
    }
}
