package com.threegap.bitnagil.data.writeroutine.service

import com.threegap.bitnagil.data.writeroutine.model.request.EditRoutineRequest
import com.threegap.bitnagil.data.writeroutine.model.request.RegisterRoutineRequest
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface WriteRoutineService {
    @POST("/api/v2/routines")
    suspend fun postRoutine(
        @Body registerRoutineRequest: RegisterRoutineRequest,
    ): BaseResponse<Unit>

    @PATCH("/api/v2/routines")
    suspend fun patchRoutine(
        @Body editRoutineRequest: EditRoutineRequest,
    ): BaseResponse<Unit>
}
