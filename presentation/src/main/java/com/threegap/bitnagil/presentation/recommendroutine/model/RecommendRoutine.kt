package com.threegap.bitnagil.presentation.recommendroutine.model

import android.os.Parcelable
import com.threegap.bitnagil.presentation.recommendroutine.type.RecommendRoutineDifficulty
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendRoutine(
    val name: String,
    val description: String,
    val difficulty: RecommendRoutineDifficulty,
    val id: String = "0",
) : Parcelable
