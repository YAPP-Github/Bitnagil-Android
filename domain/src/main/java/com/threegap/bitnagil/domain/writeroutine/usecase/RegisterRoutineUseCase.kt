package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.Time
import kotlinx.coroutines.delay
import javax.inject.Inject

class RegisterRoutineUseCase @Inject constructor() {
    suspend operator fun invoke(
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        subRoutines: List<String>,
    ): Result<Unit> {
        delay(2000L)
        return Result.success(Unit)
    }
}
