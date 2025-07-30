package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class GetRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(routineId: String): Result<Routine> =
        routineRepository.getRoutine(routineId)
}
