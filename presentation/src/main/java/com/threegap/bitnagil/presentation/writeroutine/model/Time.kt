package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    val hour: Int,
    val minute: Int,
) : Parcelable
