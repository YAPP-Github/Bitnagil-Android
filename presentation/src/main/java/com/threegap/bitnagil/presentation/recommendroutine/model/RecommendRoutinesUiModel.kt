package com.threegap.bitnagil.presentation.recommendroutine.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.recommendroutine.model.EmotionMarbleType
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutines
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendRoutinesUiModel(
    val recommendRoutinesByCategory: Map<RecommendCategory, List<RecommendRoutineUiModel>> = emptyMap(),
    val emotionMarbleType: EmotionMarbleType? = null,
) : Parcelable

fun RecommendRoutines.toUiModel() =
    RecommendRoutinesUiModel(
        recommendRoutinesByCategory = this.recommendRoutinesByCategory.mapValues { (_, routines) ->
            routines.map { it.toUiModel() }
        },
        emotionMarbleType = this.emotionMarbleType,
    )
