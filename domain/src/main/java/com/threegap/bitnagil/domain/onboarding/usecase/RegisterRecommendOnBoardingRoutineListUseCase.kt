package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class RegisterRecommendOnBoardingRoutineListUseCase @Inject constructor(
    private val repository: OnBoardingRepository,
) {
    suspend operator fun invoke(selectedRecommendRoutineIdList: List<String>) : Result<Unit> {
        return repository.registerRecommendRoutineList(selectedRecommendRoutineIdList = selectedRecommendRoutineIdList)
    }
}
