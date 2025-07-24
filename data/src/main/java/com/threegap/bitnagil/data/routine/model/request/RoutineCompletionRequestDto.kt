package com.threegap.bitnagil.data.routine.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineCompletionRequestDto(
    @SerialName("performedDate")
    val performedDate: String,
    @SerialName("routineCompletionInfos")
    val routineCompletions: List<RoutineCompletionInfoDto>,
)
