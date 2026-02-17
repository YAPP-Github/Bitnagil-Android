package com.threegap.bitnagil.data.routine.model.request

import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineCompletionInfoRequest(
    @SerialName("routineId")
    val routineId: String,
    @SerialName("routineCompleteYn")
    val routineCompleteYn: Boolean,
    @SerialName("subRoutineCompleteYn")
    val subRoutineCompleteYn: List<Boolean>,
)

internal fun RoutineCompletionInfo.toDto() =
    RoutineCompletionInfoRequest(
        routineId = this.routineId,
        routineCompleteYn = this.routineCompleteYn,
        subRoutineCompleteYn = this.subRoutineCompleteYn,
    )
