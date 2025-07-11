package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.model.OnBoarding
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingItem
import javax.inject.Inject

class GetOnBoardingListUseCase @Inject constructor() {
    operator fun invoke(): List<OnBoarding> {
        return listOf(
            OnBoarding(
                id = "1",
                title = "어떤 시간대를\n더 잘 보내고 싶나요?",
                description = null,
                onboardingItemList = listOf(
                    OnBoardingItem(
                        id = "1_1",
                        title = "아침을 잘 시작하고 싶어요",
                        description = null,
                    ),
                    OnBoardingItem(
                        id = "1_2",
                        title = "저녁을 편안하게 마무리하고 싶어요",
                        description = null,
                    ),
                    OnBoardingItem(
                        id = "1_3",
                        title = "언제든 상관 없어요",
                        description = null,
                    ),
                ),
            ),
            OnBoarding(
                id = "2",
                title = "최근 얼마나 자주\n바깥 바람을 쐬시나요?",
                description = null,
                onboardingItemList = listOf(
                    OnBoardingItem(
                        id = "2_1",
                        title = "밖에 나가지 않고 집에서만 지냈어요",
                        description = null,
                    ),
                    OnBoardingItem(
                        id = "2_2",
                        title = "잠깐 외출했어요",
                        description = null,
                    ),
                    OnBoardingItem(
                        id = "2_3",
                        title = "가끔 나가요",
                        description = null,
                    ),
                    OnBoardingItem(
                        id = "2_4",
                        title = "자주 외출해요",
                        description = null,
                    ),
                )
            ),
            OnBoarding(
                id = "3",
                title = "요즘 어떤 회복이\n필요하신가요?",
                description = "여러 개 선택할 수 있어요!",
                multipleSelectable = true,
                onboardingItemList = listOf(
                    OnBoardingItem(
                        id = "3_1",
                        title = "안정감",
                        description = "하루를 편안하게 보내고 싶어요",
                    ),
                    OnBoardingItem(
                        id = "3_2",
                        title = "연결감",
                        description = "누군가와 함꼐 있다는 느낌이 필요해요",
                    ),
                    OnBoardingItem(
                        id = "3_3",
                        title = "성장감",
                        description = "작은 변화라도 시작하고 싶어요",
                    ),
                    OnBoardingItem(
                        id = "3_4",
                        title = "생동감",
                        description = "무기력을 이겨내고 활력을 찾고싶어요",
                    ),
                )
            ),
            OnBoarding(
                id = "4",
                title = "작지만 의미 있는 변화를 위해,\n일주일에 몇 번 외출하고 싶은신가요?",
                description = "무리하지 않는 선에서, 나만의 외출 목표를 정해보세요",
                onboardingItemList = listOf(
                    OnBoardingItem(
                        id = "4_1",
                        title = "시작이 더 중요해요",
                        description = "일주일에 1회",
                    ),
                    OnBoardingItem(
                        id = "4_2",
                        title = "너무 무리하지 않아도 괜찮아요",
                        description = "일주일에 2~3회",
                    ),
                    OnBoardingItem(
                        id = "4_3",
                        title = "이 정도면 충분히 활력 있는 한 주가 될거에요",
                        description = "일주일에 4호 ㅣ아싱",
                    ),
                    OnBoardingItem(
                        id = "4_4",
                        title = "목표 선택을 도와드릴께요!",
                        description = "아직 잘 모르겠어요",
                    ),
                )
            )
        )
    }
}
