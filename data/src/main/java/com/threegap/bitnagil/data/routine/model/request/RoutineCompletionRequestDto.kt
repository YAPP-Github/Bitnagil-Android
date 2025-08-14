package com.threegap.bitnagil.data.routine.model.request

import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfos
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineCompletionRequestDto(
    @SerialName("routineCompletionInfos")
    val routineCompletionInfos: List<RoutineCompletionInfoDto>,
)

internal fun RoutineCompletionInfos.toDto() =
    RoutineCompletionRequestDto(
        routineCompletionInfos = this.routineCompletionInfos.map { it.toDto() },
    )
