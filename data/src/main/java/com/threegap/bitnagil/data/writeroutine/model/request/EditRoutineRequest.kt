package com.threegap.bitnagil.data.writeroutine.model.request

import com.threegap.bitnagil.data.writeroutine.model.dto.SubRoutineInfosDiffDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditRoutineRequest(
    @SerialName("routineId")
    val routineId: String,
    @SerialName("routineName")
    val routineName: String,
    @SerialName("repeatDay")
    val repeatDay: List<String>,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("subRoutineInfos")
    val subRoutineInfos: List<SubRoutineInfosDiffDto>,
)
