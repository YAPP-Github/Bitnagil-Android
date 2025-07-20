package com.threegap.bitnagil.presentation.writeroutine.model.navarg

import kotlinx.serialization.Serializable

@Serializable
sealed class WriteRoutineScreenArg {
    @Serializable
    data class Add(val baseRoutineId: String?) : WriteRoutineScreenArg()

    @Serializable
    data class Edit(val routineId: String) : WriteRoutineScreenArg()
}
