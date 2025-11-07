package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import java.time.LocalDate
import javax.inject.Inject

class FetchWeeklyRoutinesUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(currentWeeks: List<LocalDate>): Result<RoutineSchedule> {
        val startDate = currentWeeks.first().toString()
        val endDate = currentWeeks.last().toString()
        return routineRepository.fetchWeeklyRoutines(startDate, endDate)
    }
}
