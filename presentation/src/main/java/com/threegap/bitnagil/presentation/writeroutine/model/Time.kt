package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
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

        fun fromDomainTime(time: DomainTime): Time {
            return Time(hour = time.hour, minute = time.minute)
        }
    }

    fun toDomainTime(): DomainTime {
        return DomainTime(hour = hour, minute = minute)
    }
}
