package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class GetUserOnBoardingUseCase @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository,
) {
    suspend operator fun invoke(): Result<List<Pair<String, List<String>>>> {
        return onBoardingRepository.getUserOnBoarding()
    }
}
