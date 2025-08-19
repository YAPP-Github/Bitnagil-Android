package com.threegap.bitnagil.domain.writeroutine.repository

import com.threegap.bitnagil.domain.writeroutine.model.Date
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.RoutineUpdateType
import com.threegap.bitnagil.domain.writeroutine.model.Time
import com.threegap.bitnagil.domain.writeroutine.model.WriteRoutineEvent
import kotlinx.coroutines.flow.Flow

interface WriteRoutineRepository {
    suspend fun registerRoutine(
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        startDate: Date,
        endDate: Date,
        subRoutines: List<String>,
    ): Result<Unit>

    suspend fun editRoutine(
        routineId: String,
        routineUpdateType: RoutineUpdateType,
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        startDate: Date,
        endDate: Date,
        subRoutines: List<String>,
    ): Result<Unit>

    suspend fun getWriteRoutineEventFlow(): Flow<WriteRoutineEvent>
}
