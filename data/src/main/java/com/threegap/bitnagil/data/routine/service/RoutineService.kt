package com.threegap.bitnagil.data.routine.service

import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequest
import com.threegap.bitnagil.data.routine.model.response.RoutineResponse
import com.threegap.bitnagil.data.routine.model.response.RoutineScheduleResponse
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutineService {
    @GET("/api/v2/routines")
    suspend fun fetchRoutineSchedule(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
    ): BaseResponse<RoutineScheduleResponse>

    @GET("/api/v2/routines/{routineId}")
    suspend fun getRoutine(
        @Path("routineId") routineId: String,
    ): BaseResponse<RoutineResponse>

    @PUT("/api/v2/routines/completions")
    suspend fun routineCompletion(
        @Body request: RoutineCompletionRequest,
    ): BaseResponse<Unit>

    @DELETE("/api/v1/routines/{routineId}")
    suspend fun deleteRoutine(
        @Path("routineId") routineId: String,
    ): BaseResponse<Unit>

    @DELETE("/api/v2/routines/day/{routineId}")
    suspend fun deleteRoutineForDay(
        @Path("routineId") routineId: String,
    ): BaseResponse<Unit>
}
