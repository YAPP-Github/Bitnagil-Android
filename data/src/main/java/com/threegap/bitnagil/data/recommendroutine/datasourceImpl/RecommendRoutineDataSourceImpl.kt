package com.threegap.bitnagil.data.recommendroutine.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.recommendroutine.datasource.RecommendRoutineDataSource
import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendRoutinesDto
import com.threegap.bitnagil.data.recommendroutine.service.RecommendRoutineService
import javax.inject.Inject

class RecommendRoutineDataSourceImpl @Inject constructor(
    private val recommendRoutineService: RecommendRoutineService,
) : RecommendRoutineDataSource {

    override suspend fun fetchRecommendRoutines(): Result<RecommendRoutinesDto> =
        safeApiCall {
            recommendRoutineService.fetchRecommendRoutines()
        }
}
