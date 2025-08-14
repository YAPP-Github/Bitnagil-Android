package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfos
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class RoutineCompletionUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(routineCompletionInfos: RoutineCompletionInfos): Result<Unit> =
        routineRepository.syncRoutineCompletion(routineCompletionInfos)
}
