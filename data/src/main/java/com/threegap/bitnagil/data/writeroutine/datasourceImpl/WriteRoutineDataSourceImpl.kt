package com.threegap.bitnagil.data.writeroutine.datasourceImpl

import com.threegap.bitnagil.data.writeroutine.datasource.WriteRoutineDataSource
import com.threegap.bitnagil.data.writeroutine.model.dto.RoutineDto
import com.threegap.bitnagil.data.writeroutine.model.dto.SubRoutineDto
import com.threegap.bitnagil.data.writeroutine.model.request.EditRoutineRequest
import com.threegap.bitnagil.data.writeroutine.model.request.RegisterRoutineRequest
import kotlinx.coroutines.delay
import javax.inject.Inject

class WriteRoutineDataSourceImpl @Inject constructor(): WriteRoutineDataSource{
    override suspend fun registerRoutine(request: RegisterRoutineRequest): Result<Unit> {
        delay(2000L)
        return Result.success(Unit)
    }

    override suspend fun editRoutine(request: EditRoutineRequest): Result<Unit> {
        delay(2000L)
        return Result.success(Unit)
    }

    override suspend fun getRoutine(routineId: String): Result<RoutineDto> {
        val routineDto = RoutineDto(
            routineId = routineId,
            routineName = "임시 루틴",
            repeatDay = listOf("MON"),
            executionTime = "12:00",
            subRoutineInfos = listOf(
                SubRoutineDto(
                    subRoutineId = "id1",
                    subRoutineName = "서브루틴 1",
                    sortOrder = 1
                )
            )
        )
        return Result.success(routineDto)
    }
}
