package com.threegap.bitnagil.domain.writeroutine.model

data class Routine(
    val id: String,
    val name: String,
    val subRoutines: List<SubRoutine>,
    val repeatDays: List<RepeatDay>,
    val endDate: Date,
    val startTime: Time,
)
