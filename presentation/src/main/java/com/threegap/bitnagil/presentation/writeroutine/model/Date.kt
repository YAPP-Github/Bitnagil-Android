package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Date(
    val year: Int,
    val month: Int,
    val day: Int
) : Parcelable {
    companion object {
        fun now() = Date(
            year = java.time.LocalDate.now().year,
            month = java.time.LocalDate.now().monthValue,
            day = java.time.LocalDate.now().dayOfMonth
        )
    }
}
