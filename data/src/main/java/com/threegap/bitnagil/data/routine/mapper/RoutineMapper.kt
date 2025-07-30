package com.threegap.bitnagil.data.routine.mapper

import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionInfoDto
import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequestDto
import com.threegap.bitnagil.data.routine.model.response.RoutineDto
import com.threegap.bitnagil.data.routine.model.response.RoutinesResponseDto
import com.threegap.bitnagil.data.routine.model.response.SubRoutineDto
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.domain.routine.model.RoutineCompletion
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.RoutineType
import com.threegap.bitnagil.domain.routine.model.Routines
import com.threegap.bitnagil.domain.routine.model.SubRoutine

// toDomain
internal fun RoutinesResponseDto.toDomain() =
    Routines(
        routinesByDate = this.routines.mapValues { (_, routineDto) ->
            routineDto.map { it.toDomain() }
        },
    )

internal fun RoutineDto.toDomain() =
    Routine(
        routineId = this.routineId,
        historySeq = this.historySeq,
        routineName = this.routineName,
        executionTime = this.executionTime,
        subRoutines = this.subRoutines.sortedBy { it.sortOrder }.map { it.toDomain() },
        isModified = this.isModified,
        routineCompletionId = this.routineCompletionId,
        isCompleted = this.isCompleted,
        repeatDay = this.repeatDay.map { DayOfWeek.fromString(it) },
        routineType = RoutineType.fromString(this.routineType),
    )

internal fun SubRoutineDto.toDomain() =
    SubRoutine(
        subRoutineId = this.subRoutineId,
        historySeq = this.historySeq,
        subRoutineName = this.subRoutineName,
        isModified = this.isModified,
        sortOrder = this.sortOrder,
        routineCompletionId = this.routineCompletionId,
        isCompleted = this.isCompleted,
        routineType = RoutineType.fromString(this.routineType),
    )

// toDto
internal fun RoutineCompletion.toDto() =
    RoutineCompletionRequestDto(
        performedDate = this.performedDate,
        routineCompletions = this.routineCompletions.map { it.toDto() },
    )

internal fun RoutineCompletionInfo.toDto() =
    RoutineCompletionInfoDto(
        routineType = this.routineType.name,
        routineId = this.routineId,
        historySeq = this.historySeq,
        isCompleted = this.isCompleted,
    )
