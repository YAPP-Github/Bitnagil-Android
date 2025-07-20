package com.threegap.bitnagil.data.writeroutine.model.dto

import com.threegap.bitnagil.domain.writeroutine.model.SubRoutineDiff
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubRoutineInfosDiffDto(
    @SerialName("subRoutineId")
    val subRoutineId: String?,
    @SerialName("subRoutineName")
    val subRoutineName: String?,
    @SerialName("sortOrder")
    val sortOrder: Int?,
) {
    companion object {
        fun fromSubRoutineDiff(subRoutineDiff: SubRoutineDiff): SubRoutineInfosDiffDto {
            return SubRoutineInfosDiffDto(
                subRoutineId = subRoutineDiff.id,
                subRoutineName = subRoutineDiff.name,
                sortOrder = subRoutineDiff.sort,
            )
        }
    }
}
