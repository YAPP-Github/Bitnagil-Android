package com.threegap.bitnagil.data.routine.service

import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequestDto
import com.threegap.bitnagil.data.routine.model.response.RoutinesResponseDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutineService {
    @GET("/api/v1/routines")
    suspend fun fetchRoutines(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): BaseResponse<RoutinesResponseDto>

    @POST("/api/v1/routines/completions")
    suspend fun routineCompletion(
        @Body request: RoutineCompletionRequestDto
    ): BaseResponse<Unit>

    @DELETE("/api/v1/routines/{routineId}")
    suspend fun deleteRoutine(
        @Path("routineId") routineId: String
    ): BaseResponse<Unit>
}
