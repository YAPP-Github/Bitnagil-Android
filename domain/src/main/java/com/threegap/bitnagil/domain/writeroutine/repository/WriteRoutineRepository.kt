package com.threegap.bitnagil.domain.writeroutine.repository

import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutineDiff
import com.threegap.bitnagil.domain.writeroutine.model.Time
import com.threegap.bitnagil.domain.writeroutine.model.WriteRoutineEvent
import kotlinx.coroutines.flow.Flow

interface WriteRoutineRepository {
    suspend fun registerRoutine(
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        subRoutines: List<String>,
    ): Result<Unit>

    suspend fun editRoutine(
        routineId: String,
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        subRoutines: List<SubRoutineDiff>,
    ): Result<Unit>

    suspend fun getWriteRoutineEventFlow(): Flow<WriteRoutineEvent>
}
