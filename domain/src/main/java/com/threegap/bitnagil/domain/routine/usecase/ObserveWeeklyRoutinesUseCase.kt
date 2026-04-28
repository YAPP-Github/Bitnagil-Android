package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveWeeklyRoutinesUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    operator fun invoke(startDate: String, endDate: String): Flow<RoutineSchedule> =
        routineRepository.observeWeeklyRoutines(startDate, endDate)
}
