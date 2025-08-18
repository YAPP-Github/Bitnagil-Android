package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class DeleteRoutineForDayUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(routineId: String): Result<Unit> =
        routineRepository.deleteRoutineForDay(routineId)
}
