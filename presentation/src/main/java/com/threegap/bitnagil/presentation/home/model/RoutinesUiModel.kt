package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutinesUiModel(
    val routinesByDate: Map<String, List<RoutineUiModel>>,
) : Parcelable

