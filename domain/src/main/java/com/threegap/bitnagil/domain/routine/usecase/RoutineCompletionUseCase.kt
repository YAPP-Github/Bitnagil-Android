package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.RoutineCompletion
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class RoutineCompletionUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(routineCompletion: RoutineCompletion): Result<Unit> =
        routineRepository.syncRoutineCompletion(routineCompletion)
}
