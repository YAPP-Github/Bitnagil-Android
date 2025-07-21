package com.threegap.bitnagil.presentation.home.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val koreanLocale = Locale.KOREAN
private val monthYearFormatter = DateTimeFormatter.ofPattern("yyyy년 M월", koreanLocale)

fun LocalDate.getCurrentWeekDays(): List<LocalDate> =
    (0..6).map { this.with(DayOfWeek.MONDAY).plusDays(it.toLong()) }

fun LocalDate.formatMonthYear(): String =
    this.format(monthYearFormatter)

fun LocalDate.formatDayOfWeekShort(): String =
    this.dayOfWeek.getDisplayName(TextStyle.SHORT, koreanLocale)

fun LocalDate.formatDayOfMonth(): String =
    this.dayOfMonth.toString()
