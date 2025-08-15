package com.threegap.bitnagil.data.routine.service

import com.threegap.bitnagil.data.routine.model.request.RoutineByDayDeletionRequestDto
import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequestDto
import com.threegap.bitnagil.data.routine.model.response.RoutineDto
import com.threegap.bitnagil.data.routine.model.response.RoutinesResponseDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutineService {
    @GET("/api/v2/routines")
    suspend fun fetchRoutines(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
    ): BaseResponse<RoutinesResponseDto>

    @PUT("/api/v2/routines")
    suspend fun routineCompletion(
        @Body request: RoutineCompletionRequestDto,
    ): BaseResponse<Unit>

    @DELETE("/api/v1/routines/{routineId}")
    suspend fun deleteRoutine(
        @Path("routineId") routineId: String,
    ): BaseResponse<Unit>

    @GET("/api/v1/routines/{routineId}")
    suspend fun getRoutine(
        @Path("routineId") routineId: String,
    ): BaseResponse<RoutineDto>

    @HTTP(method = "DELETE", path = "/api/v1/routines/day", hasBody = true)
    suspend fun deleteRoutineByDay(
        @Body request: RoutineByDayDeletionRequestDto,
    ): BaseResponse<Unit>
}
