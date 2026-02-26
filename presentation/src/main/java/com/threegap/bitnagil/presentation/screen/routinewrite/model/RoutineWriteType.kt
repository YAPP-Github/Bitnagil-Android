package com.threegap.bitnagil.presentation.screen.routinewrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class RoutineWriteType : Parcelable {
    @Parcelize
    data object Add : RoutineWriteType()

    @Parcelize
    data class Edit(val updateRoutineFromNowDate: Boolean) : RoutineWriteType()
}
