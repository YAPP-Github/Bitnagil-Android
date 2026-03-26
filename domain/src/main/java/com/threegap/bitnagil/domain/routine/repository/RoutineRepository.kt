package com.threegap.bitnagil.domain.routine.repository

import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.RoutineEditInfo
import com.threegap.bitnagil.domain.routine.model.RoutineRegisterInfo
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun observeWeeklyRoutines(startDate: String, endDate: String): Flow<RoutineSchedule>
    suspend fun applyRoutineToggle(dateKey: String, routineId: String, completionInfo: RoutineCompletionInfo)
    suspend fun getRoutine(routineId: String): Result<Routine>
    suspend fun deleteRoutine(routineId: String): Result<Unit>
    suspend fun deleteRoutineForDay(routineId: String): Result<Unit>
    suspend fun registerRoutine(routineRegisterInfo: RoutineRegisterInfo): Result<Unit>
    suspend fun editRoutine(routineEditInfo: RoutineEditInfo): Result<Unit>
}
