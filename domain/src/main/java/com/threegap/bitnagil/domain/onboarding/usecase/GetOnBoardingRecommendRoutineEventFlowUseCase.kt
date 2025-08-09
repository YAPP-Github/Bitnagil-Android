package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutineEvent
import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnBoardingRecommendRoutineEventFlowUseCase @Inject constructor(
    private val repository: OnBoardingRepository,
) {
    suspend operator fun invoke(): Flow<OnBoardingRecommendRoutineEvent> {
        return repository.getOnBoardingRecommendRoutineEventFlow()
    }
}
