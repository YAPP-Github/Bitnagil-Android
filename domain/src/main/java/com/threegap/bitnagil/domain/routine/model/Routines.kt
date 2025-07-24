package com.threegap.bitnagil.domain.routine.model

data class Routines(
    val routinesByDate: Map<String, List<Routine>>
) {
    fun withSortedSubRoutines(): Routines =
        copy(
            routinesByDate = routinesByDate.mapValues { (_, routinesList) ->
                routinesList.map { it.withSortedSubRoutines() }
            }
        )
}
