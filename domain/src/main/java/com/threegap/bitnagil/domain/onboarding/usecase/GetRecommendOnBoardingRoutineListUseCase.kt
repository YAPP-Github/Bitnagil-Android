package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingItem
import javax.inject.Inject

class GetRecommendOnBoardingRoutineListUseCase @Inject constructor() {
    operator fun invoke() : List<OnBoardingItem> {
        return listOf(
            OnBoardingItem(
                id = "1",
                title = "루틴명",
                description = "세부 루틴 한 줄 설명",
            ),
            OnBoardingItem(
                id = "2",
                title = "루틴명",
                description = "세부 루틴 한 줄 설명",
            ),
            OnBoardingItem(
                id = "3",
                title = "루틴명",
                description = "세부 루틴 한 줄 설명",
            )
        )
    }
}
