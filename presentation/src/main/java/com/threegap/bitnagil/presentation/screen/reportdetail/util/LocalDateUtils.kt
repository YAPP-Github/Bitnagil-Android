package com.threegap.bitnagil.presentation.screen.reportdetail.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toPresentationFormatInReportDetail(): String {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd (E)", Locale.KOREAN)
    return this.format(formatter)
}
