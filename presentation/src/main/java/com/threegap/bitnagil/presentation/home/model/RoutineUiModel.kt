package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutineUiModel(
    val routineId: String,
    val routineName: String,
    val executionTime: String,
    val subRoutineUiModels: List<SubRoutineUiModel>,
    val isModified: Boolean = false,
    val isCompleted: Boolean = false,
) : Parcelable

