package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.model.OnBoarding
import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class GetOnBoardingsUseCase @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository,
) {
    suspend operator fun invoke(): List<OnBoarding> {
        return onBoardingRepository.getOnBoardingList()
    }
}
