package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class RegisterRecommendOnBoardingRoutinesUseCase @Inject constructor(
    private val repository: OnBoardingRepository,
) {
    suspend operator fun invoke(selectedRecommendRoutineIds: List<String>) : Result<Unit> {
        return repository.registerRecommendRoutineList(selectedRecommendRoutineIds = selectedRecommendRoutineIds)
    }
}
