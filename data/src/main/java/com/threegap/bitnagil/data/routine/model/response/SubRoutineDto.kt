package com.threegap.bitnagil.data.routine.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubRoutineDto(
    @SerialName("subRoutineId")
    val subRoutineId: String,
    @SerialName("historySeq")
    val historySeq: Int,
    @SerialName("subRoutineName")
    val subRoutineName: String,
    @SerialName("modifiedYn")
    val isModified: Boolean,
    @SerialName("sortOrder")
    val sortOrder: Int,
    @SerialName("completeYn")
    val isCompleted: Boolean,
    @SerialName("routineType")
    val routineType: String,
)
