package com.threegap.bitnagil.data.routine.datasource

import com.threegap.bitnagil.data.routine.model.request.RoutineByDayDeletionRequestDto
import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequestDto
import com.threegap.bitnagil.data.routine.model.response.RoutineDto
import com.threegap.bitnagil.data.routine.model.response.RoutinesResponseDto

interface RoutineRemoteDataSource {
    suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutinesResponseDto>
    suspend fun syncRoutineCompletion(routineCompletionRequestDto: RoutineCompletionRequestDto): Result<Unit>
    suspend fun deleteRoutine(routineId: String): Result<Unit>
    suspend fun deleteRoutineByDay(routineByDayDeletionRequestDto: RoutineByDayDeletionRequestDto): Result<Unit>
    suspend fun getRoutine(routineId: String): Result<RoutineDto>
}
