package com.threegap.bitnagil.presentation.screen.reporthistory.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toPresentationFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd E", Locale.KOREAN)
    return this.format(formatter)
}
