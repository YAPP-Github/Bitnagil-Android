package com.threegap.bitnagil.presentation.writeroutine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class WriteRoutineType : Parcelable {
    @Parcelize
    data object Add : WriteRoutineType()

    @Parcelize
    data class Edit(val updateRoutineFromNowDate: Boolean): WriteRoutineType()
}
