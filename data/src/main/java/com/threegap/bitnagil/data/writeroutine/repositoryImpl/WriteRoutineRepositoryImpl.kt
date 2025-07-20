package com.threegap.bitnagil.data.writeroutine.repositoryImpl

import com.threegap.bitnagil.data.writeroutine.datasource.WriteRoutineDataSource
import com.threegap.bitnagil.data.writeroutine.model.dto.SubRoutineInfosDiffDto
import com.threegap.bitnagil.data.writeroutine.model.request.EditRoutineRequest
import com.threegap.bitnagil.data.writeroutine.model.request.RegisterRoutineRequest
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.Routine
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutineDiff
import com.threegap.bitnagil.domain.writeroutine.model.Time
import com.threegap.bitnagil.domain.writeroutine.repository.WriteRoutineRepository
import javax.inject.Inject

class WriteRoutineRepositoryImpl @Inject constructor(
    private val writeRoutineDataSource: WriteRoutineDataSource,
) : WriteRoutineRepository {
    override suspend fun registerRoutine(name: String, repeatDay: List<RepeatDay>, startTime: Time, subRoutines: List<String>): Result<Unit> {
        val request = RegisterRoutineRequest(
            routineName = name,
            repeatDay = repeatDay.map { it.name },
            executionTime = startTime.toString(),
            subRoutineName = subRoutines,
        )
        return writeRoutineDataSource.registerRoutine(request)
    }

    override suspend fun editRoutine(
        routineId: String,
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        subRoutines: List<SubRoutineDiff>,
    ): Result<Unit> {
        val request = EditRoutineRequest(
            routineId = routineId,
            routineName = name,
            repeatDay = repeatDay.map { it.name },
            executionTime = startTime.toString(),
            subRoutineInfos = subRoutines.map {
                SubRoutineInfosDiffDto.fromSubRoutineDiff(it)
            },
        )

        return writeRoutineDataSource.editRoutine(request)
    }

    override suspend fun getRoutine(routineId: String): Result<Routine> {
        return writeRoutineDataSource.getRoutine(routineId).map { routineDto ->
            routineDto.toRoutine()
        }
    }
}
