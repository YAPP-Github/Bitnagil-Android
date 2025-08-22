package com.threegap.bitnagil.data.routine.model.response

import com.threegap.bitnagil.domain.routine.model.Routines
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutinesResponseDto(
    @SerialName("routines")
    val routines: Map<String, DayRoutinesDto>,
)

fun RoutinesResponseDto.toDomain() =
    Routines(
        routines = this.routines.mapValues { (_, dayRoutinesDto) ->
            dayRoutinesDto.toDomain()
        },
    )
