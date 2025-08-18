package com.threegap.bitnagil.domain.writeroutine.model

data class Date(
    val year: Int,
    val month: Int,
    val day: Int,
) {
    init {
        require(year in 1970..2099) { "Year must be in range 1970..2099, but was $year" }
        require(month in 1..12) { "Month must be in range 1..12, but was $month" }
        require(day in 1..31) { "Day must be in range 1..31, but was $day" }
    }

    fun toFormattedString(): String {
        return "%04d-%02d-%02d".format(year, month, day)
    }
}
