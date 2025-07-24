package com.threegap.bitnagil.data.routine.service

import com.threegap.bitnagil.data.routine.model.response.RoutinesResponseDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RoutineService {
    @GET("/api/v1/routines")
    suspend fun fetchRoutines(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): BaseResponse<RoutinesResponseDto>
}
