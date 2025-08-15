package com.threegap.bitnagil.presentation.writeroutine.model

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendSubRoutine

data class SubRoutine(
    val id: String,
    val name: String,
) {
    companion object {
        fun fromDomainRecommendSubRoutine(subRoutine: RecommendSubRoutine): SubRoutine {
            return SubRoutine(
                id = subRoutine.id.toString(),
                name = subRoutine.name,
            )
        }
    }
}
