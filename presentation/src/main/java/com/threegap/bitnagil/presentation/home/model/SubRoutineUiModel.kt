package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubRoutineUiModel(
    val subRoutineId: String,
    val subRoutineName: String,
    val sortOrder: Int,
    val isCompleted: Boolean = false,
    val isModified: Boolean = false,
) : Parcelable

