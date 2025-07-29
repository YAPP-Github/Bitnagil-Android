package com.threegap.bitnagil.presentation.writeroutine.model
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendSubRoutine
import com.threegap.bitnagil.domain.routine.model.SubRoutine as DomainSubRoutine

data class SubRoutine(
    val id: String,
    val name: String,
) {
    companion object {
        fun fromDomainSubRoutine(subRoutine: DomainSubRoutine): SubRoutine {
            return SubRoutine(
                id = subRoutine.subRoutineId,
                name = subRoutine.subRoutineName,
            )
        }

        fun fromDomainRecommendSubRoutine(subRoutine: RecommendSubRoutine): SubRoutine {
            return SubRoutine(
                id = subRoutine.id.toString(),
                name = subRoutine.name,
            )
        }
    }
}
