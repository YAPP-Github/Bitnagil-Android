package com.threegap.bitnagil.data.routine.datasource

import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequestDto
import com.threegap.bitnagil.data.routine.model.response.RoutinesResponseDto

interface RoutineRemoteDataSource {
    suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutinesResponseDto>
    suspend fun syncRoutineCompletion(routineCompletionRequestDto: RoutineCompletionRequestDto): Result<Unit>
    suspend fun deleteRoutine(routineId: String): Result<Unit>
}
