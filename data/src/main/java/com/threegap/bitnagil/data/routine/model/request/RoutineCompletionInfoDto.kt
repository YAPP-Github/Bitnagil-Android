package com.threegap.bitnagil.data.routine.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineCompletionInfoDto(
    @SerialName("routineType")
    val routineType: String,
    @SerialName("routineId")
    val routineId: String,
    @SerialName("historySeq")
    val historySeq: Int,
    @SerialName("completeYn")
    val isCompleted: Boolean,
)
