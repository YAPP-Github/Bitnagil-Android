package com.threegap.bitnagil.data.writeroutine.model.request

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRoutineRequest(
    @SerialName("routineName")
    val routineName: String,
    @SerialName("repeatDay")
    val repeatDay: List<RepeatDay>,
    @SerialName("routineStartDate")
    val routineStartDate: String,
    @SerialName("routineEndDate")
    val routineEndDate: String,
    @SerialName("executionTime")
    val executionTime: String,
    @SerialName("subRoutineName")
    val subRoutineName: List<String>,
    @SerialName("recommendedRoutineType")
    val recommendedRoutineType: RecommendCategory?,
)
