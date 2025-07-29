package com.threegap.bitnagil.presentation.writeroutine.model
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendSubRoutine
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutine as DomainSubRoutine

data class SubRoutine(
    val id: String,
    val name: String,
) {
    companion object {
        fun fromDomainSubRoutine(subRoutine: DomainSubRoutine): SubRoutine {
            return SubRoutine(
                id = subRoutine.id,
                name = subRoutine.name,
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
