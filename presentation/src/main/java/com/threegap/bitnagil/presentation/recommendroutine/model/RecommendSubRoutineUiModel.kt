package com.threegap.bitnagil.presentation.recommendroutine.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendSubRoutine
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendSubRoutineUiModel(
    val id: Int = 0,
    val name: String = "",
) : Parcelable

fun RecommendSubRoutine.toUiModel() =
    RecommendSubRoutineUiModel(
        id = this.id,
        name = this.name,
    )
