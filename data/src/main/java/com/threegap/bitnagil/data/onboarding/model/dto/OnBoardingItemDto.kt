package com.threegap.bitnagil.data.onboarding.model.dto

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingItem

data class OnBoardingItemDto(
    val id: String,
    val title: String,
    val description: String?,
) {
    fun toOnBoardingItem(): OnBoardingItem {
        return OnBoardingItem(
            id = id,
            title = title,
            description = description,
        )
    }

    companion object {
        val Morning = OnBoardingItemDto(
            id = "08:00:00",
            title = "아침을 잘 시작하고 싶어요",
            description = null,
        )

        val Evening = OnBoardingItemDto(
            id = "20:00:00",
            title = "저녁을 편안하게 마무리하고 싶어요",
            description = null,
        )

        val Nothing = OnBoardingItemDto(
            id = "00:00:00",
            title = "언제든 상관 없어요",
            description = null,
        )

        val RealOutingZeroPerWeek = OnBoardingItemDto(
            id = "NEVER",
            title = "밖에 나가지 않고 집에서만 지냈어요",
            description = null,
        )

        val RealOutingOneToTwoPerWeek = OnBoardingItemDto(
            id = "SHORT",
            title = "잠깐 외출했어요",
            description = null,
        )

        val RealOutingThreeToFourPerWeek = OnBoardingItemDto(
            id = "SOMETIMES",
            title = "가끔 나가요",
            description = null,
        )

        val RealOutingMoreThanFivePerWeek = OnBoardingItemDto(
            id = "OFTEN",
            title = "자주 외출해요",
            description = null,
        )

        val Stability = OnBoardingItemDto(
            id = "STABILITY",
            title = "안정감",
            description = "하루를 편안하게 보내고 싶어요",
        )

        val Connectedness = OnBoardingItemDto(
            id = "CONNECTEDNESS",
            title = "연결감",
            description = "누군가와 함께 있다는 느낌이 필요해요",
        )

        val Vitality = OnBoardingItemDto(
            id = "VITALITY",
            title = "생동감",
            description = "무기력을 이겨내고 활력을 찾고싶어요",
        )

        val Growth = OnBoardingItemDto(
            id = "GROWTH",
            title = "성장감",
            description = "작은 변화라도 시작하고 싶어요",
        )

        val TargetOutingOneToTwoPerWeek = OnBoardingItemDto(
            id = "ONE_PER_WEEK",
            title = "시작이 더 중요해요",
            description = "일주일에 1회",
        )

        val TargetOutingThreeToFourPerWeek = OnBoardingItemDto(
            id = "TWO_TO_THREE_PER_WEEK",
            title = "너무 무리하지 않아도 괜찮아요",
            description = "일주일에 2~3회",
        )

        val TargetOutingMoreThenFivePerWeek = OnBoardingItemDto(
            id = "MORE_THAN_FOUR_PER_WEEK",
            title = "이 정도면 충분히 활력 있는 한 주가 될거에요",
            description = "일주일에 4회 이상",
        )

        val TargetOutingUnknown = OnBoardingItemDto(
            id = "UNKNOWN",
            title = "목표 선택을 도와드릴께요!",
            description = "아직 잘 모르겠어요",
        )
    }
}
