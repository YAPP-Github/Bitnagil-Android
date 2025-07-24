package com.threegap.bitnagil.domain.routine.model

data class Routines(
    val routinesByDate: Map<String, List<Routine>>
)
