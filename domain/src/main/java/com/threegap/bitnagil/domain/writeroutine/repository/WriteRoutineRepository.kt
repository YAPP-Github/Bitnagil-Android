package com.threegap.bitnagil.domain.writeroutine.repository

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.RoutineUpdateType
import com.threegap.bitnagil.domain.writeroutine.model.WriteRoutineEvent
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface WriteRoutineRepository {
    suspend fun registerRoutine(
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: LocalTime,
        startDate: LocalDate,
        endDate: LocalDate,
        subRoutines: List<String>,
        recommendedRoutineType: RecommendCategory?,
    ): Result<Unit>

    suspend fun editRoutine(
        routineId: String,
        routineUpdateType: RoutineUpdateType,
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: LocalTime,
        startDate: LocalDate,
        endDate: LocalDate,
        subRoutines: List<String>,
    ): Result<Unit>

    suspend fun getWriteRoutineEventFlow(): Flow<WriteRoutineEvent>
}
