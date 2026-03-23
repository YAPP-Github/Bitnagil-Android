package com.threegap.bitnagil.data.recommendroutine.datasourceImpl

import com.threegap.bitnagil.data.recommendroutine.datasource.RecommendRoutineDataSource
import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendRoutinesResponse
import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendedRoutineResponse
import com.threegap.bitnagil.data.recommendroutine.service.RecommendRoutineService
import javax.inject.Inject

class RecommendRoutineDataSourceImpl @Inject constructor(
    private val recommendRoutineService: RecommendRoutineService,
) : RecommendRoutineDataSource {

    override suspend fun fetchRecommendRoutines(): Result<RecommendRoutinesResponse> =
        recommendRoutineService.fetchRecommendRoutines()

    override suspend fun getRecommendRoutine(recommendRoutineId: Long): Result<RecommendedRoutineResponse> =
        recommendRoutineService.getRecommendRoutine(recommendRoutineId)
}
