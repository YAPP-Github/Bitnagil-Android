package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.RoutineEditInfo
import com.threegap.bitnagil.domain.routine.model.RoutineUpdateType
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class EditRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(
        routineId: String,
        routineUpdateType: RoutineUpdateType,
        name: String,
        repeatDay: List<DayOfWeek>,
        startTime: LocalTime,
        startDate: LocalDate,
        endDate: LocalDate,
        subRoutines: List<String>,
    ): Result<Unit> {
        return routineRepository.editRoutine(
            routineEditInfo = RoutineEditInfo(
                id = routineId,
                updateType = routineUpdateType,
                name = name,
                repeatDay = repeatDay,
                startTime = startTime,
                startDate = startDate,
                endDate = endDate,
                subRoutines = subRoutines,
            ),
        )
    }
}
