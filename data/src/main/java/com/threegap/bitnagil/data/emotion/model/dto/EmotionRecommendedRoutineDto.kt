package com.threegap.bitnagil.data.emotion.model.dto

import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmotionRecommendedRoutineDto(
    @SerialName("recommendedRoutineId")
    val recommendedRoutineId: Int,
    @SerialName("recommendedRoutineName")
    val recommendedRoutineName: String,
    @SerialName("recommendedRoutineDescription")
    val recommendedRoutineDescription: String,
    @SerialName("recommendedSubRoutineSearchResult")
    val recommendedSubRoutineSearchResult: List<EmotionRecommendedSubRoutineDto>,
) {
    fun toEmotionRecommendRoutine(): EmotionRecommendRoutine {
        return EmotionRecommendRoutine(
            routineId = recommendedRoutineId.toString(),
            routineName = recommendedRoutineName,
            routineDescription = recommendedRoutineDescription,
        )
    }
}
