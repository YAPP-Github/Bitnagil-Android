package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectableDay(
    val day: Day,
    val selected: Boolean,
) : Parcelable
