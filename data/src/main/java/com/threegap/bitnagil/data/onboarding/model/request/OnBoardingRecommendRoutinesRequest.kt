package com.threegap.bitnagil.data.onboarding.model.request

data class OnBoardingRecommendRoutinesRequest(
    val timeSlot: String,
    val emotionType: String,
    val realOutingFrequency: String,
    val targetOutingFrequency: String,
)
