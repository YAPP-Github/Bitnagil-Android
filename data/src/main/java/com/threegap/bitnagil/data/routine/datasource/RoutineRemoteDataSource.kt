package com.threegap.bitnagil.data.routine.datasource

import com.threegap.bitnagil.data.routine.model.response.RoutinesResponseDto

interface RoutineRemoteDataSource {
    suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutinesResponseDto>
}
