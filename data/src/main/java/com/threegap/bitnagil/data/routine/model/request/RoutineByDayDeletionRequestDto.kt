package com.threegap.bitnagil.data.routine.model.request

import com.threegap.bitnagil.domain.routine.model.RoutineByDayDeletion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineByDayDeletionRequestDto(
    @SerialName("routineCompletionId")
    val routineCompletionId: Int?,
    @SerialName("routineId")
    val routineId: String,
    @SerialName("routineType")
    val routineType: String,
    @SerialName("subRoutineInfosForDelete")
    val subRoutineInfosForDelete: List<SubRoutineDeletionInfoDto>,
    @SerialName("performedDate")
    val performedDate: String,
    @SerialName("historySeq")
    val historySeq: Int,
)

fun RoutineByDayDeletion.toDto() =
    RoutineByDayDeletionRequestDto(
        routineCompletionId = this.routineCompletionId,
        routineId = this.routineId,
        routineType = this.routineType.name,
        subRoutineInfosForDelete = this.subRoutineInfosForDelete.map { it.toDto() },
        performedDate = this.performedDate,
        historySeq = this.historySeq,
    )
