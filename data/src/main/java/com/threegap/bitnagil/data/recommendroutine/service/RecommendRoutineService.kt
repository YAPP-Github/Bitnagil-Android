package com.threegap.bitnagil.data.recommendroutine.service

import com.threegap.bitnagil.data.recommendroutine.model.response.RecommendRoutinesDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.GET

interface RecommendRoutineService {
    @GET("/api/v1/recommend-routines")
    suspend fun fetchRecommendRoutines(): BaseResponse<RecommendRoutinesDto>
}
