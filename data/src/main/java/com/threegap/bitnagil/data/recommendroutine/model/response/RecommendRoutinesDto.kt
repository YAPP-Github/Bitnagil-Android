package com.threegap.bitnagil.data.recommendroutine.model.response

import com.threegap.bitnagil.domain.recommendroutine.model.EmotionMarbleType
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutines
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendRoutinesDto(
    @SerialName("recommendedRoutines")
    val recommendedRoutinesByCategory: Map<String, List<RecommendedRoutineDto>>,
    @SerialName("emotionMarbleType")
    val emotionMarbleType: String?,
)

fun RecommendRoutinesDto.toDomain(): RecommendRoutines =
    RecommendRoutines(
        recommendRoutinesByCategory = this.recommendedRoutinesByCategory.map { (categoryString, routines) ->
            RecommendCategory.fromString(categoryString) to routines.map { it.toDomain() }
        }.toMap(),
        emotionMarbleType = this.emotionMarbleType?.let { EmotionMarbleType.valueOf(it) },
    )
