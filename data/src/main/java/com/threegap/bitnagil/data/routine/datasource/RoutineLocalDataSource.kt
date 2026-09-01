package com.threegap.bitnagil.data.routine.datasource

interface RoutineLocalDataSource {
    suspend fun markFirstRoutineCompletion(): Result<Boolean>
}
