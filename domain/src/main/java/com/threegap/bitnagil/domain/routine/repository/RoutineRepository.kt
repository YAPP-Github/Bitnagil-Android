package com.threegap.bitnagil.domain.routine.repository

import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfos
import com.threegap.bitnagil.domain.routine.model.RoutineEditInfo
import com.threegap.bitnagil.domain.routine.model.RoutineRegisterInfo
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import com.threegap.bitnagil.domain.routine.model.WriteRoutineEvent
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutineSchedule>
    suspend fun syncRoutineCompletion(routineCompletionInfos: RoutineCompletionInfos): Result<Unit>
    suspend fun getRoutine(routineId: String): Result<Routine>
    suspend fun deleteRoutine(routineId: String): Result<Unit>
    suspend fun deleteRoutineForDay(routineId: String): Result<Unit>
    suspend fun registerRoutine(routineRegisterInfo: RoutineRegisterInfo): Result<Unit>
    suspend fun editRoutine(routineEditInfo: RoutineEditInfo): Result<Unit>
    suspend fun getWriteRoutineEventFlow(): Flow<WriteRoutineEvent>
}
