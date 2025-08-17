package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.Date
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.Time
import com.threegap.bitnagil.domain.writeroutine.repository.WriteRoutineRepository
import javax.inject.Inject

class RegisterRoutineUseCase @Inject constructor(
    private val writeRoutineRepository: WriteRoutineRepository,
) {
    suspend operator fun invoke(
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        startDate: Date?,
        endDate: Date?,
        subRoutines: List<String>,
    ): Result<Unit> {
        return writeRoutineRepository.registerRoutine(
            name = name,
            repeatDay = repeatDay,
            startTime = startTime,
            startDate = startDate,
            endDate = endDate,
            subRoutines = subRoutines,
        )
    }
}
