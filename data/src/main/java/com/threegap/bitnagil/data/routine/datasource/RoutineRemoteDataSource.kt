package com.threegap.bitnagil.data.routine.datasource

import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequest
import com.threegap.bitnagil.data.routine.model.request.RoutineEditRequest
import com.threegap.bitnagil.data.routine.model.request.RoutineRegisterRequest
import com.threegap.bitnagil.data.routine.model.response.RoutineResponse
import com.threegap.bitnagil.data.routine.model.response.RoutineScheduleResponse

interface RoutineRemoteDataSource {
    suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutineScheduleResponse>
    suspend fun syncRoutineCompletion(routineCompletionRequest: RoutineCompletionRequest): Result<Unit>
    suspend fun getRoutine(routineId: String): Result<RoutineResponse>
    suspend fun deleteRoutine(routineId: String): Result<Unit>
    suspend fun deleteRoutineForDay(routineId: String): Result<Unit>
    suspend fun registerRoutine(request: RoutineRegisterRequest): Result<Unit>
    suspend fun editRoutine(request: RoutineEditRequest): Result<Unit>
}
