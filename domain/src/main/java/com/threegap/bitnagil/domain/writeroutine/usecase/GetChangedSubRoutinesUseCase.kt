package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.SubRoutine
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutineDiff
import javax.inject.Inject

class GetChangedSubRoutinesUseCase @Inject constructor() {
    operator fun invoke(
        oldSubRoutines: List<SubRoutine>,
        newSubRoutineNames: List<String>,
    ): List<SubRoutineDiff> {
        val oldSubRoutineMap = oldSubRoutines.associateBy { it.name }
        val changedSubRoutines = mutableListOf<SubRoutineDiff>()

        for ((index, name) in newSubRoutineNames.withIndex()) {
            val sort = index + 1

            val oldSubRoutine = oldSubRoutineMap[name]
            if (oldSubRoutine == null) {
                changedSubRoutines.add(
                    SubRoutineDiff(
                        id = null,
                        name = name,
                        sort = sort
                    )
                )
            } else if (oldSubRoutine.sort != sort) {
                changedSubRoutines.add(
                    SubRoutineDiff(
                        id = oldSubRoutine.id,
                        name = oldSubRoutine.name,
                        sort = sort
                    )
                )
            }
        }

        val newSubRoutineNameSet = newSubRoutineNames.toSet()

        for ((name, newSubRoutine) in oldSubRoutineMap) {
            if (newSubRoutineNameSet.contains(name)) {
                continue
            } else {
                changedSubRoutines.add(
                    SubRoutineDiff(
                        id = newSubRoutine.id,
                        name = null,
                        sort = null
                    )
                )
            }
        }

        return changedSubRoutines
    }
}
