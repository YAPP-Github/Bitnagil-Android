package com.threegap.bitnagil.presentation.screen.routinewrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ExpandableSection : Parcelable {
    SUB_ROUTINE, REPEAT_DAYS, PERIOD, START_TIME, NONE
}
