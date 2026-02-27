package com.threegap.bitnagil.presentation.screen.routinewrite.model.navarg

import kotlinx.serialization.Serializable

@Serializable
sealed class RoutineWriteScreenArg {
    @Serializable
    data class Add(val baseRoutineId: String?) : RoutineWriteScreenArg()

    @Serializable
    data class Edit(val routineId: String, val updateRoutineFromNowDate: Boolean) : RoutineWriteScreenArg()
}
