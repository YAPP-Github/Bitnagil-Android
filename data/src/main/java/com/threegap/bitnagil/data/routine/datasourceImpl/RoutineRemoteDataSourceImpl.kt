package com.threegap.bitnagil.data.routine.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.common.safeUnitApiCall
import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequest
import com.threegap.bitnagil.data.routine.model.response.RoutineResponse
import com.threegap.bitnagil.data.routine.model.response.RoutineScheduleResponse
import com.threegap.bitnagil.data.routine.service.RoutineService
import javax.inject.Inject

class RoutineRemoteDataSourceImpl @Inject constructor(
    private val routineService: RoutineService,
) : RoutineRemoteDataSource {
    override suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutineScheduleResponse> =
        safeApiCall {
            routineService.fetchRoutineSchedule(startDate, endDate)
        }

    override suspend fun syncRoutineCompletion(routineCompletionRequest: RoutineCompletionRequest): Result<Unit> =
        safeUnitApiCall {
            routineService.routineCompletion(routineCompletionRequest)
        }

    override suspend fun getRoutine(routineId: String): Result<RoutineResponse> =
        safeApiCall {
            routineService.getRoutine(routineId)
        }

    override suspend fun deleteRoutine(routineId: String): Result<Unit> =
        safeUnitApiCall {
            routineService.deleteRoutine(routineId)
        }

    override suspend fun deleteRoutineForDay(routineId: String): Result<Unit> =
        safeUnitApiCall {
            routineService.deleteRoutineForDay(routineId)
        }
}
