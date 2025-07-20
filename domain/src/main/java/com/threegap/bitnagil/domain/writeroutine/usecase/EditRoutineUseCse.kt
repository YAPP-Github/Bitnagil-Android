package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutineDiff
import com.threegap.bitnagil.domain.writeroutine.model.Time
import kotlinx.coroutines.delay
import javax.inject.Inject

class EditRoutineUseCse @Inject constructor(){
    suspend operator fun invoke(
        routineId: String,
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: Time,
        subRoutines: List<SubRoutineDiff>,
    ): Result<Unit>  {
        delay(2000L)
        return Result.success(Unit)
    }
}
