package com.threegap.bitnagil.data.routine.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineDto(
    @SerialName("routineId")
    val routineId: String,
    @SerialName("historySeq")
    val historySeq: Int,
    @SerialName("routineName")
    val routineName: String,
    @SerialName("repeatDay")
    val repeatDay: List<String>,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("subRoutineSearchResultDto")
    val subRoutines: List<SubRoutineDto>,
    @SerialName("modifiedYn")
    val isModified: Boolean,
    @SerialName("routineCompletionId")
    val routineCompletionId: Int?,
    @SerialName("completeYn")
    val isCompleted: Boolean,
    @SerialName("routineType")
    val routineType: String,
)
