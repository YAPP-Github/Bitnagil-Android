package com.threegap.bitnagil.domain.recommendroutine.usecase

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine
import com.threegap.bitnagil.domain.recommendroutine.repository.RecommendRoutineRepository
import javax.inject.Inject

class GetRecommendRoutineUseCase @Inject constructor(
    private val recommendRoutineRepository: RecommendRoutineRepository,
) {
    suspend operator fun invoke(recommendRoutineId: String): Result<RecommendRoutine> =
        recommendRoutineRepository.getRecommendRoutine(recommendRoutineId)
}
