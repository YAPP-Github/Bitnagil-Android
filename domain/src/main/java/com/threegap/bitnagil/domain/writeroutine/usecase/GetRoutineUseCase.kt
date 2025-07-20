package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.Date
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.model.Routine
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutine
import com.threegap.bitnagil.domain.writeroutine.model.Time
import javax.inject.Inject

class GetRoutineUseCase @Inject constructor() {
    suspend operator fun invoke(
        routineId: String
    ): Result<Routine>{
        val routine = Routine(
            id = routineId,
            name = "임시 루틴",
            subRoutines = listOf(
                SubRoutine(
                    id = "id1",
                    name = "서브루틴 1",
                    sort = 1
                )
            ),
            repeatDays = listOf(
                RepeatDay.MON,
            ),
            endDate = Date(year = 2099, month = 12, day = 31),
            startTime = Time(hour = 12, minute = 0)
        )

        return Result.success(routine)
    }

}
