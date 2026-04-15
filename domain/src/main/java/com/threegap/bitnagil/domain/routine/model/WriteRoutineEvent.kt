package com.threegap.bitnagil.domain.routine.model

sealed interface WriteRoutineEvent {
    data object AddRoutine : WriteRoutineEvent
    data class EditRoutine(val routineId: String) : WriteRoutineEvent
}
