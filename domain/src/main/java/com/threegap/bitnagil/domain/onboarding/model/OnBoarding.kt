package com.threegap.bitnagil.domain.onboarding.model

data class OnBoarding(
    val id: String,
    val title: String,
    val description: String?,
    val onboardingItemList: List<OnBoardingItem>,
    val multipleSelectable: Boolean = false,
)
