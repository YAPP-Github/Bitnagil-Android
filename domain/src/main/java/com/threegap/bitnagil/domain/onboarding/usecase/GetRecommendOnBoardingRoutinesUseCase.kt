package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutine
import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class GetRecommendOnBoardingRoutinesUseCase @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) {
    suspend operator fun invoke(selectedItemIdsWithOnBoardingId: List<Pair<String, List<String>>>) : Result<List<OnBoardingRecommendRoutine>> {
        return onBoardingRepository.getRecommendOnBoardingRouteList(selectedItemIdsWithOnBoardingId)
    }
}
