package com.threegap.bitnagil.data.recommendroutine.datasource

import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendRoutinesResponse
import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendedRoutineResponse

interface RecommendRoutineDataSource {
    suspend fun fetchRecommendRoutines(): Result<RecommendRoutinesResponse>
    suspend fun getRecommendRoutine(recommendRoutineId: Long): Result<RecommendedRoutineResponse>
}
