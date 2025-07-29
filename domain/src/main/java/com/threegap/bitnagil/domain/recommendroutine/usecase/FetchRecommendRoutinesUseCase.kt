package com.threegap.bitnagil.domain.recommendroutine.usecase

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutines
import com.threegap.bitnagil.domain.recommendroutine.repository.RecommendRoutineRepository
import javax.inject.Inject

class FetchRecommendRoutinesUseCase @Inject constructor(
    private val recommendRoutineRepository: RecommendRoutineRepository,
) {
    suspend operator fun invoke(): Result<RecommendRoutines> =
        recommendRoutineRepository.fetchRecommendRoutines()
}
