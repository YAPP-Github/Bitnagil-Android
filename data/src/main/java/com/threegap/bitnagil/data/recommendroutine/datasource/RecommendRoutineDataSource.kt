package com.threegap.bitnagil.data.recommendroutine.datasource

import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendRoutinesDto
import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendedRoutineDto

interface RecommendRoutineDataSource {
    suspend fun fetchRecommendRoutines(): Result<RecommendRoutinesDto>
    suspend fun getRecommendRoutine(recommendRoutineId: Int): Result<RecommendedRoutineDto>
}
