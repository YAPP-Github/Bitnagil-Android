package com.threegap.bitnagil.presentation.screen.routinewrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Date(
    val year: Int,
    val month: Int,
    val day: Int,
) : Parcelable {
    companion object {
        fun now(): Date {
            val currentDate = LocalDate.now()
            return Date(
                year = currentDate.year,
                month = currentDate.monthValue,
                day = currentDate.dayOfMonth,
            )
        }

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

        fun fromString(dateString: String): Date {
            val (year, month, day) = dateString.split("-").map { it.toInt() }
            return Date(year, month, day)
        }
    }

    fun toFormattedString(): String = "%04d.%02d.%02d".format(year, month, day)
    fun toYearShrinkageFormattedString(): String = "%02d.%02d.%02d".format((year % 100), month, day)

    fun checkInRange(startDate: Date?, endDate: Date?): Boolean {
        val current = toLocalDate()
        val start = startDate?.toLocalDate() ?: LocalDate.of(2000, 1, 1)
        val end = endDate?.toLocalDate() ?: LocalDate.of(2999, 12, 31)

        return !current.isBefore(start) && !current.isAfter(end)
    }

    fun toLocalDate(): LocalDate {
        return LocalDate.of(year, month, day)
    }
}
