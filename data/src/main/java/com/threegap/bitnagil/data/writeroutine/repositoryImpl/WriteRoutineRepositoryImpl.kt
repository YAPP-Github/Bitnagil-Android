package com.threegap.bitnagil.data.writeroutine.repositoryImpl

import com.threegap.bitnagil.data.writeroutine.datasource.WriteRoutineDataSource
import com.threegap.bitnagil.data.writeroutine.model.request.EditRoutineRequest
import com.threegap.bitnagil.data.writeroutine.model.request.RegisterRoutineRequest
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.RoutineUpdateType
import com.threegap.bitnagil.domain.writeroutine.model.WriteRoutineEvent
import com.threegap.bitnagil.domain.writeroutine.repository.WriteRoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class WriteRoutineRepositoryImpl @Inject constructor(
    private val writeRoutineDataSource: WriteRoutineDataSource,
) : WriteRoutineRepository {
    override suspend fun registerRoutine(
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: LocalTime,
        startDate: LocalDate,
        endDate: LocalDate,
        subRoutines: List<String>,
        recommendedRoutineType: String?,
    ): Result<Unit> {
        val request = RegisterRoutineRequest(
            routineName = name,
            repeatDay = repeatDay.map { it.fullName },
            executionTime = startTime.toString(),
            routineStartDate = startDate.toString(),
            routineEndDate = endDate.toString(),
            subRoutineName = subRoutines,
            recommendedRoutineType = recommendedRoutineType,
        )
        return writeRoutineDataSource.registerRoutine(request).also {
            if (it.isSuccess) {
                _writeRoutineEventFlow.emit(WriteRoutineEvent.AddRoutine)
            }
        }
    }

    override suspend fun editRoutine(
        routineId: String,
        routineUpdateType: RoutineUpdateType,
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: LocalTime,
        startDate: LocalDate,
        endDate: LocalDate,
        subRoutines: List<String>,
    ): Result<Unit> {
        val request = EditRoutineRequest(
            routineId = routineId,
            updateApplyDate = routineUpdateType.value,
            routineName = name,
            repeatDay = repeatDay.map { it.fullName },
            executionTime = startTime.toString(),
            routineStartDate = startDate.toString(),
            routineEndDate = endDate.toString(),
            subRoutineName = subRoutines,
        )

        return writeRoutineDataSource.editRoutine(request).also {
            if (it.isSuccess) {
                _writeRoutineEventFlow.emit(WriteRoutineEvent.EditRoutine(routineId))
            }
        }
    }

    private val _writeRoutineEventFlow = MutableSharedFlow<WriteRoutineEvent>()
    override suspend fun getWriteRoutineEventFlow(): Flow<WriteRoutineEvent> = _writeRoutineEventFlow.asSharedFlow()
}
