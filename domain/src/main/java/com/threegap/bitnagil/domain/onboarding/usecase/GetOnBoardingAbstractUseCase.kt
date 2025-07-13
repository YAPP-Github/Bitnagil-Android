package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstract
import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class GetOnBoardingAbstractUseCase @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository,
) {
    suspend operator fun invoke(selectedItemIdsWithOnBoardingId: List<Pair<String, List<String>>>): OnBoardingAbstract {
        return onBoardingRepository.getOnBoardingAbstract(selectedItemIdsWithOnBoardingId)
    }
}
