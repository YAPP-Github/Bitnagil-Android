package com.threegap.bitnagil.data.routine.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.service.RoutineService
import javax.inject.Inject

class RoutineRemoteDataSourceImpl @Inject constructor(
    private val routineService: RoutineService
) : RoutineRemoteDataSource {
    override suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutinesResponseDto> =
        safeApiCall {
            routineService.fetchRoutines(startDate, endDate)
        }
}
