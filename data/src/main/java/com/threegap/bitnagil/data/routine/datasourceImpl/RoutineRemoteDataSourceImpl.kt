package com.threegap.bitnagil.data.routine.datasourceImpl

import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequest
import com.threegap.bitnagil.data.routine.model.request.RoutineEditRequest
import com.threegap.bitnagil.data.routine.model.request.RoutineRegisterRequest
import com.threegap.bitnagil.data.routine.model.response.RoutineResponse
import com.threegap.bitnagil.data.routine.model.response.RoutineScheduleResponse
import com.threegap.bitnagil.data.routine.service.RoutineService
import javax.inject.Inject

class RoutineRemoteDataSourceImpl @Inject constructor(
    private val routineService: RoutineService,
) : RoutineRemoteDataSource {
    override suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutineScheduleResponse> =
        routineService.fetchRoutineSchedule(startDate, endDate)

    override suspend fun syncRoutineCompletion(routineCompletionRequest: RoutineCompletionRequest): Result<Unit> =
        routineService.routineCompletion(routineCompletionRequest)

    override suspend fun getRoutine(routineId: String): Result<RoutineResponse> =
        routineService.getRoutine(routineId)

    override suspend fun deleteRoutine(routineId: String): Result<Unit> =
        routineService.deleteRoutine(routineId)

    override suspend fun deleteRoutineForDay(routineId: String): Result<Unit> =
        routineService.deleteRoutineForDay(routineId)

    override suspend fun registerRoutine(request: RoutineRegisterRequest): Result<Unit> =
        routineService.postRoutine(request)

    override suspend fun editRoutine(request: RoutineEditRequest): Result<Unit> =
        routineService.patchRoutine(request)
}
