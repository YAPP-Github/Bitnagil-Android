package com.threegap.bitnagil.data.writeroutine.datasource

import com.threegap.bitnagil.data.writeroutine.model.dto.RoutineDto
import com.threegap.bitnagil.data.writeroutine.model.request.EditRoutineRequest
import com.threegap.bitnagil.data.writeroutine.model.request.RegisterRoutineRequest

interface WriteRoutineDataSource {
    suspend fun registerRoutine(request: RegisterRoutineRequest): Result<Unit>
    suspend fun editRoutine(request: EditRoutineRequest): Result<Unit>
    suspend fun getRoutine(routineId: String): Result<RoutineDto>
}
