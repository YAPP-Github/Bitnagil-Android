package com.threegap.bitnagil.domain.recommendroutine.repository

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutines

interface RecommendRoutineRepository {
    suspend fun fetchRecommendRoutines(): Result<RecommendRoutines>
    suspend fun getRecommendRoutine(recommendRoutineId: String): Result<RecommendRoutine>
}
