package com.threegap.bitnagil.data.writeroutine.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRoutineRequest(
    @SerialName("routineName")
    val routineName: String,
    @SerialName("repeatDay")
    val repeatDay: List<String>,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("subRoutineName")
    val subRoutineName: List<String>,
)
