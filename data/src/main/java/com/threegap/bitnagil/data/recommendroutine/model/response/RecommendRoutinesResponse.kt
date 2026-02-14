package com.threegap.bitnagil.data.recommendroutine.model.response

import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutines
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendRoutinesResponse(
    @SerialName("recommendedRoutines")
    val recommendedRoutinesByCategory: Map<RecommendCategory, List<RecommendedRoutineResponse>>,
    @SerialName("emotionMarbleType")
    val emotionMarbleType: EmotionMarbleType?,
)

fun RecommendRoutinesResponse.toDomain(): RecommendRoutines =
    RecommendRoutines(
        recommendRoutinesByCategory = this.recommendedRoutinesByCategory.mapValues { (_, routines) ->
            routines.map { it.toDomain() }
        },
        emotionMarbleType = this.emotionMarbleType,
    )
