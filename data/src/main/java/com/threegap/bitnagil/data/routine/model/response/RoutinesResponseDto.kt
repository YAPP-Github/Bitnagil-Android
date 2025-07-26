package com.threegap.bitnagil.data.routine.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutinesResponseDto(
    @SerialName("routines")
    val routines: Map<String, List<RoutineDto>>,
)
