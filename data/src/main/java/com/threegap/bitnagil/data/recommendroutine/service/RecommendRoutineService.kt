package com.threegap.bitnagil.data.recommendroutine.service

import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendRoutinesResponse
import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendedRoutineResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendRoutineService {
    @GET("/api/v1/recommend-routines")
    suspend fun fetchRecommendRoutines(): Result<RecommendRoutinesResponse>

    @GET("/api/v1/recommend-routines/{recommendedRoutineId}")
    suspend fun getRecommendRoutine(
        @Path("recommendedRoutineId") recommendedRoutineId: Long,
    ): Result<RecommendedRoutineResponse>
}
