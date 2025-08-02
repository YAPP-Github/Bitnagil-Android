package com.threegap.bitnagil.domain.writeroutine.model

data class Time(
    val hour: Int,
    val minute: Int,
) {
    init {
        require(hour in 0..23) { "Hour must be in range 0..23, but was $hour" }
        require(minute in 0..59) { "Minute must be in range 0..59, but was $minute" }
    }

    fun toFormattedString(): String = "%02d:%02d:00".format(hour, minute)
}
