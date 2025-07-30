package com.threegap.bitnagil.domain.routine.repository

import com.threegap.bitnagil.domain.routine.model.RoutineByDayDeletion
import com.threegap.bitnagil.domain.routine.model.RoutineCompletion
import com.threegap.bitnagil.domain.routine.model.Routines

interface RoutineRepository {
    suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<Routines>
    suspend fun syncRoutineCompletion(routineCompletion: RoutineCompletion): Result<Unit>
    suspend fun deleteRoutine(routineId: String): Result<Unit>
    suspend fun deleteRoutineByDay(routineByDayDeletion: RoutineByDayDeletion): Result<Unit>
}
