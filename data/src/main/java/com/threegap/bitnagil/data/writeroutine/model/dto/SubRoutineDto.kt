package com.threegap.bitnagil.data.writeroutine.model.dto

import com.threegap.bitnagil.domain.writeroutine.model.SubRoutine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubRoutineDto(
    @SerialName("subRoutineId")
    val subRoutineId: String,
    @SerialName("subRoutineName")
    val subRoutineName: String,
    @SerialName("sortOrder")
    val sortOrder: Int,
) {
    fun toSubRoutine(): SubRoutine {
        return SubRoutine(
            id = subRoutineId,
            name = subRoutineName,
            sort = sortOrder,
        )
    }
}
