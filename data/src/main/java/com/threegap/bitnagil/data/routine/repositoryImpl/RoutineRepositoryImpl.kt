package com.threegap.bitnagil.data.routine.repositoryImpl

import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.model.request.toDto
import com.threegap.bitnagil.data.routine.model.response.toDomain
import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfos
import com.threegap.bitnagil.domain.routine.model.RoutineEditInfo
import com.threegap.bitnagil.domain.routine.model.RoutineRegisterInfo
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import com.threegap.bitnagil.domain.routine.model.WriteRoutineEvent
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineRemoteDataSource: RoutineRemoteDataSource,
) : RoutineRepository {
    override suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutineSchedule> =
        routineRemoteDataSource.fetchWeeklyRoutines(startDate, endDate)
            .map { it.toDomain() }

    override suspend fun syncRoutineCompletion(routineCompletionInfos: RoutineCompletionInfos): Result<Unit> =
        routineRemoteDataSource.syncRoutineCompletion(routineCompletionInfos.toDto())

    override suspend fun getRoutine(routineId: String): Result<Routine> =
        routineRemoteDataSource.getRoutine(routineId).map { it.toDomain() }

    override suspend fun deleteRoutine(routineId: String): Result<Unit> =
        routineRemoteDataSource.deleteRoutine(routineId)

    override suspend fun deleteRoutineForDay(routineId: String): Result<Unit> =
        routineRemoteDataSource.deleteRoutineForDay(routineId)

    override suspend fun registerRoutine(routineRegisterInfo: RoutineRegisterInfo): Result<Unit> {
        val request = routineRegisterInfo.toDto()
        return routineRemoteDataSource.registerRoutine(request).also {
            if (it.isSuccess) {
                _writeRoutineEventFlow.emit(WriteRoutineEvent.AddRoutine)
            }
        }
    }

    override suspend fun editRoutine(routineEditInfo: RoutineEditInfo): Result<Unit> {
        val request = routineEditInfo.toDto()
        return routineRemoteDataSource.editRoutine(request).also {
            if (it.isSuccess) {
                _writeRoutineEventFlow.emit(WriteRoutineEvent.EditRoutine(routineEditInfo.id))
            }
        }
    }

    private val _writeRoutineEventFlow = MutableSharedFlow<WriteRoutineEvent>()
    override suspend fun getWriteRoutineEventFlow(): Flow<WriteRoutineEvent> = _writeRoutineEventFlow.asSharedFlow()
}
