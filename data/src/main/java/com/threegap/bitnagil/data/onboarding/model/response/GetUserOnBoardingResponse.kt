package com.threegap.bitnagil.data.onboarding.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserOnBoardingResponse(
    @SerialName("timeSlot")
    val timeSlot: String,
    @SerialName("emotionTypes")
    val emotionTypes: List<String>,
    @SerialName("targetOutingFrequency")
    val targetOutingFrequency: String,
)
