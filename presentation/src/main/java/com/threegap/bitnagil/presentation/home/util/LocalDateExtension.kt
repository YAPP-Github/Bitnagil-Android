package com.threegap.bitnagil.presentation.home.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val koreanLocale = Locale.KOREAN
private val monthYearFormatter = DateTimeFormatter.ofPattern("yyyy년 M월", koreanLocale)
private val executionTimeFormatter12 = DateTimeFormatter.ofPattern("a h:mm", koreanLocale)
private val executionTimeFormatter24 = DateTimeFormatter.ofPattern("HH:mm", koreanLocale)

fun LocalDate.getCurrentWeekDays(): List<LocalDate> =
    (0..6).map { this.with(DayOfWeek.MONDAY).plusDays(it.toLong()) }

fun LocalDate.formatMonthYear(): String =
    this.format(monthYearFormatter)

fun LocalDate.formatDayOfWeekShort(): String =
    this.dayOfWeek.getDisplayName(TextStyle.SHORT, koreanLocale)

fun LocalDate.formatDayOfMonth(): String =
    this.dayOfMonth.toString()

fun String.formatExecutionTime24Hour(): String =
    try {
        val time = LocalTime.parse(this)
        if (time == LocalTime.MIDNIGHT) {
            "하루\n종일"
        } else {
            time.format(executionTimeFormatter24)
        }
    } catch (e: Exception) {
        "시간 미정"
    }

fun String.formatExecutionTime12Hour(): String =
    try {
        val time = LocalTime.parse(this)
        if (time == LocalTime.MIDNIGHT) {
            "하루종일"
        } else {
            time.format(executionTimeFormatter12)
        }
    } catch (e: Exception) {
        "시간 미정"
    }
