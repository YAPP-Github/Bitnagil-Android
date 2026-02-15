package com.threegap.bitnagil.data.routine.model.request

import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfos
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineCompletionRequest(
    @SerialName("routineCompletionInfos")
    val routineCompletionInfos: List<RoutineCompletionInfoRequest>,
)

internal fun RoutineCompletionInfos.toDto() =
    RoutineCompletionRequest(
        routineCompletionInfos = this.routineCompletionInfos.map { it.toDto() },
    )
