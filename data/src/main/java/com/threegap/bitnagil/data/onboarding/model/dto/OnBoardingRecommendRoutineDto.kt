package com.threegap.bitnagil.data.onboarding.model.dto

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnBoardingRecommendRoutineDto(
    @SerialName("recommendedRoutineId")
    val recommendedRoutineId: Int,
    @SerialName("recommendedRoutineName")
    val recommendedRoutineName: String,
    @SerialName("routineDescription")
    val routineDescription: String,
    @SerialName("recommendedSubRoutines")
    val recommendedSubRoutines: List<OnBoardingRecommendSubRoutineDto>,
) {
    fun toOnBoardingRecommendRoutine(): OnBoardingRecommendRoutine {
        return OnBoardingRecommendRoutine(
            id = "$recommendedRoutineId",
            name = recommendedRoutineName,
            description = routineDescription,
        )
    }
}
