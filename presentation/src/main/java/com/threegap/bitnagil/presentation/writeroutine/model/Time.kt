package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.text.format
import com.threegap.bitnagil.domain.writeroutine.model.Time as DomainTime

@Parcelize
data class Time(
    val hour: Int,
    val minute: Int,
) : Parcelable {
    init {
        require(hour in 0..23) { "Hour must be in range 0..23, but was $hour" }
        require(minute in 0..59) { "Minute must be in range 0..59, but was $minute" }
    }

    companion object {
        val Init = Time(hour = 12, minute = 0)
        val AllDay = Time(hour = 0, minute = 0)

        fun fromDomainTimeString(timeString: String): Time {
            try {
                val (hour, minute) = timeString.split(":").map { it.toInt() }
                return Time(hour = hour, minute = minute)
            } catch (_: Exception) {
                return Time(hour = 12, minute = 0)
            }
        }
    }

    fun toDomainTime(): DomainTime {
        return DomainTime(hour = hour, minute = minute)
    }

    fun toAmPmFormattedString(): String {
        val amPm: String
        var displayHour = hour

        if (hour == 0) { // 자정 (오전 12시)
            amPm = "오전"
            displayHour = 12
        } else if (hour == 12) { // 정오 (오후 12시)
            amPm =  "오후"
            displayHour = 12
        } else if (hour > 12) {
            amPm = "오후"
            displayHour -= 12
        } else {
            amPm =  "오전"
        }

        return "%s %d:%02d".format(amPm, displayHour, minute)
    }
}
