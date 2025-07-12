package com.threegap.bitnagil.data.onboarding.model.dto

import com.threegap.bitnagil.domain.onboarding.model.OnBoarding

data class OnBoardingDto(
    val id: String,
    val title: String,
    val description: String?,
    val onBoardingDtoList: List<OnBoardingItemDto>,
    val multipleSelectable: Boolean,
) {
    fun toOnBoarding(): OnBoarding {
        return OnBoarding(
            id = id,
            title = title,
            description = description,
            onboardingItemList = onBoardingDtoList.map { it.toOnBoardingItem() },
            multipleSelectable = multipleSelectable,
        )
    }

    companion object {
        val TimeSlot = OnBoardingDto(
            id = "TimeSlot",
            title = "어떤 시간대를\n더 잘 보내고 싶나요?",
            description = null,
            multipleSelectable = false,
            onBoardingDtoList = listOf(
                OnBoardingItemDto.Morning,
                OnBoardingItemDto.Evening,
                OnBoardingItemDto.Nothing,
            ),
        )

        val RealOutingFrequency = OnBoardingDto(
            id = "RealOutingFrequency",
            title = "최근 얼마나 자주\n바깥 바람을 쐬시나요?",
            description = null,
            multipleSelectable = false,
            onBoardingDtoList = listOf(
                OnBoardingItemDto.RealOutingZeroPerWeek,
                OnBoardingItemDto.RealOutingOneToTwoPerWeek,
                OnBoardingItemDto.RealOutingThreeToFourPerWeek,
                OnBoardingItemDto.RealOutingMoreThanFivePerWeek,
            )
        )

        val EmotionType = OnBoardingDto(
            id = "EmotionType",
            title = "요즘 어떤 회복이\n필요하신가요?",
            description = "여러 개 선택할 수 있어요!",
            multipleSelectable = true,
            onBoardingDtoList = listOf(
                OnBoardingItemDto.Stability,
                OnBoardingItemDto.Connectedness,
                OnBoardingItemDto.Vitality,
                OnBoardingItemDto.Growth,
            )
        )

        val TargetOutingFrequency = OnBoardingDto(
            id = "TargetOutingFrequency",
            title = "작지만 의미 있는 변화를 위해,\n일주일에 몇 번 외출하고 싶은신가요?",
            description = "무리하지 않는 선에서, 나만의 외출 목표를 정해보세요",
            multipleSelectable = false,
            onBoardingDtoList = listOf(
                OnBoardingItemDto.TargetOutingOneToTwoPerWeek,
                OnBoardingItemDto.TargetOutingThreeToFourPerWeek,
                OnBoardingItemDto.TargetOutingMoreThenFivePerWeek,
                OnBoardingItemDto.TargetOutingUnknown,
            )
        )
    }
}
