package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class FetchWeeklyRoutinesUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(startDate: String, endDate: String): Result<RoutineSchedule> {
        return routineRepository.fetchWeeklyRoutines(startDate, endDate)
    }
}
