package com.threegap.bitnagil.data.routine.model.request

import com.threegap.bitnagil.domain.routine.model.SubRoutineDeletionInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubRoutineDeletionInfoDto(
    @SerialName("routineCompletionId")
    val routineCompletionId: Int?,
    @SerialName("subRoutineId")
    val subRoutineId: String,
)

fun SubRoutineDeletionInfo.toDto() =
    SubRoutineDeletionInfoDto(
        routineCompletionId = this.routineCompletionId,
        subRoutineId = this.subRoutineId,
    )
