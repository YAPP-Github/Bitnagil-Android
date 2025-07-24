package com.threegap.bitnagil.domain.routine.repository

import com.threegap.bitnagil.domain.routine.model.Routines

interface RoutineRepository {
    suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<Routines>
}
