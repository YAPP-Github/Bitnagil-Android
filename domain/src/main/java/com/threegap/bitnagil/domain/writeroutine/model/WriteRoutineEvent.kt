package com.threegap.bitnagil.domain.writeroutine.model

sealed interface WriteRoutineEvent {
    data object AddRoutine : WriteRoutineEvent
    data class EditRoutine(val routineId: String) : WriteRoutineEvent
}
