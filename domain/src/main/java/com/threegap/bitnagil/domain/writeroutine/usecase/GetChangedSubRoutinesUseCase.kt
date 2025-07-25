package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.SubRoutine
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutineDiff
import javax.inject.Inject

class GetChangedSubRoutinesUseCase @Inject constructor() {
    operator fun invoke(
        oldSubRoutines: List<SubRoutine>,
        newSubRoutineNames: List<String>,
    ): List<SubRoutineDiff> {
        val oldSubRoutineMap = oldSubRoutines.groupBy { it.name }.toMutableMap()
        val changedSubRoutines = mutableListOf<SubRoutineDiff>()

        val newSubRoutineNameCountMap = newSubRoutineNames
            .groupingBy { it }
            .eachCount()
            .toMutableMap()

        for (oldSubRoutine in oldSubRoutines) {
            val name = oldSubRoutine.name
            val count = newSubRoutineNameCountMap[name] ?: 0

            if (count > 0) {
                newSubRoutineNameCountMap[name] = count - 1
                continue
            } else {
                changedSubRoutines.add(
                    SubRoutineDiff(
                        id = oldSubRoutine.id,
                        name = null,
                        sort = null,
                    ),
                )
            }
        }

        for ((index, name) in newSubRoutineNames.withIndex()) {
            val sort = index + 1

            val oldSubRoutinesWithSameName = oldSubRoutineMap[name]
            val oldSubRoutine = oldSubRoutinesWithSameName?.firstOrNull()
            if (oldSubRoutine == null) {
                changedSubRoutines.add(
                    SubRoutineDiff(
                        id = null,
                        name = name,
                        sort = sort,
                    ),
                )
            } else if (oldSubRoutine.sort != sort) {
                changedSubRoutines.add(
                    SubRoutineDiff(
                        id = oldSubRoutine.id,
                        name = oldSubRoutine.name,
                        sort = sort,
                    ),
                )
                oldSubRoutineMap[name] = oldSubRoutinesWithSameName.drop(1)
            } else {
                oldSubRoutineMap[name] = oldSubRoutinesWithSameName.drop(1)
            }
        }

        return changedSubRoutines
    }
}
