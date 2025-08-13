package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Date(
    val year: Int,
    val month: Int,
    val day: Int
) : Parcelable {
    companion object {
        fun now() = Date(
            year = java.time.LocalDate.now().year,
            month = java.time.LocalDate.now().monthValue,
            day = java.time.LocalDate.now().dayOfMonth
        )

        fun min(d1: Date, d2: Date): Date {
            if (d1.year < d2.year) return d1
            if (d1.year > d2.year) return d2

            if (d1.month < d2.month) return d1
            if (d1.month > d2.month) return d2

            if (d1.day < d2.day) return d1

            return d2
        }

        fun max(d1: Date, d2: Date): Date {
            if (d1.year > d2.year) return d1
            if (d1.year < d2.year) return d2

            if (d1.month > d2.month) return d1
            if (d1.month < d2.month) return d2

            if (d1.day > d2.day) return d1

            return d2
        }
    }

    fun toFormattedString(): String = "%04d.%02d.%02d".format(year, month, day)
    fun toYearShrinkageFormattedString(): String = "%02d.%02d.%02d".format((year % 100), month, day)

    fun checkInRange(startDate: Date?, endDate: Date?) : Boolean {
        val appliedStartDate = startDate ?: Date(year = 2000, month = 1, day = 1)
        val appliedEndDate = endDate ?: Date(year = 2999, month = 12, day = 31)

        val startValue = appliedStartDate.year * 10000 + appliedStartDate.month * 100 + appliedStartDate.day
        val endValue = appliedEndDate.year * 10000 + appliedEndDate.month * 100 + appliedEndDate.day
        val targetValue = year * 10000 + month * 100 + day

        return targetValue in startValue..endValue
    }
}
