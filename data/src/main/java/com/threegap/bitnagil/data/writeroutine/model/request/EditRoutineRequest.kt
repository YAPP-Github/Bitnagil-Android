package com.threegap.bitnagil.data.writeroutine.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditRoutineRequest(
    @SerialName("routineId")
    val routineId: String,
    @SerialName("updateApplyDate")
    val updateApplyDate: String,
    @SerialName("routineName")
    val routineName: String,
    @SerialName("repeatDay")
    val repeatDay: List<String>,
    @SerialName("routineStartDate")
    val routineStartDate: String,
    @SerialName("routineEndDate")
    val routineEndDate: String,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("subRoutineName")
    val subRoutineName: List<String>,
)
