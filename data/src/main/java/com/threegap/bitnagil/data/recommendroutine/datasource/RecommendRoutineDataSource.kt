package com.threegap.bitnagil.data.recommendroutine.datasource

import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendRoutinesDto

interface RecommendRoutineDataSource {
    suspend fun fetchRecommendRoutines(): Result<RecommendRoutinesDto>
}
