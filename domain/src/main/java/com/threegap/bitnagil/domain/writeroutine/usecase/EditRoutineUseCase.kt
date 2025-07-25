package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutineDiff
import com.threegap.bitnagil.domain.writeroutine.model.Time
import com.threegap.bitnagil.domain.writeroutine.repository.WriteRoutineRepository
import javax.inject.Inject

class EditRoutineUseCase @Inject constructor(
    private val writeRoutineRepository: WriteRoutineRepository,
) {
    suspend operator fun invoke(
        routineId: String,
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        subRoutines: List<SubRoutineDiff>,
    ): Result<Unit> {
        return writeRoutineRepository.editRoutine(
            routineId = routineId,
            name = name,
            repeatDay = repeatDay,
            startTime = startTime,
            subRoutines = subRoutines,
        )
    }
}
