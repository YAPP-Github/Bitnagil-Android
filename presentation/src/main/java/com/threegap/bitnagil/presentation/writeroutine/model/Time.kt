package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    val hour: Int,
    val minute: Int,
) : Parcelable {
    companion object {
        val Init = Time(hour = 12, minute = 0)
        val AllDay = Time(hour = 23, minute = 59)
    }
}
