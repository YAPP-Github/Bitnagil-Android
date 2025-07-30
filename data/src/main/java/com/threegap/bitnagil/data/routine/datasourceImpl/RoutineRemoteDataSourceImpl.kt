package com.threegap.bitnagil.data.routine.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.common.safeUnitApiCall
import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.model.request.RoutineByDayDeletionRequestDto
import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequestDto
import com.threegap.bitnagil.data.routine.model.response.RoutinesResponseDto
import com.threegap.bitnagil.data.routine.service.RoutineService
import javax.inject.Inject

class RoutineRemoteDataSourceImpl @Inject constructor(
    private val routineService: RoutineService,
) : RoutineRemoteDataSource {
    override suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutinesResponseDto> =
        safeApiCall {
            routineService.fetchRoutines(startDate, endDate)
        }

    override suspend fun syncRoutineCompletion(routineCompletionRequestDto: RoutineCompletionRequestDto): Result<Unit> =
        safeUnitApiCall {
            routineService.routineCompletion(routineCompletionRequestDto)
        }

    override suspend fun deleteRoutine(routineId: String): Result<Unit> =
        safeUnitApiCall {
            routineService.deleteRoutine(routineId)
        }

    override suspend fun deleteRoutineByDay(routineByDayDeletionRequestDto: RoutineByDayDeletionRequestDto): Result<Unit> =
        safeUnitApiCall {
            routineService.deleteRoutineByDay(routineByDayDeletionRequestDto)
        }
}
