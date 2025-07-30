package com.threegap.bitnagil.data.recommendroutine.repositoryImpl

import com.threegap.bitnagil.data.recommendroutine.datasource.RecommendRoutineDataSource
import com.threegap.bitnagil.data.recommendroutine.model.response.toDomain
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutine
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutines
import com.threegap.bitnagil.domain.recommendroutine.repository.RecommendRoutineRepository
import javax.inject.Inject

class RecommendRoutineRepositoryImpl @Inject constructor(
    private val recommendRoutineDataSource: RecommendRoutineDataSource,
) : RecommendRoutineRepository {
    override suspend fun fetchRecommendRoutines(): Result<RecommendRoutines> =
        recommendRoutineDataSource.fetchRecommendRoutines()
            .map { it.toDomain() }

    override suspend fun getRecommendRoutine(recommendRoutineId: String): Result<RecommendRoutine> {
        val recommendRoutineIdInt = recommendRoutineId.toIntOrNull() ?: return Result.failure(
            IllegalArgumentException("recommendRoutineId is not a valid integer"),
        )

        return recommendRoutineDataSource.getRecommendRoutine(recommendRoutineIdInt)
            .map { it.toDomain() }
    }
}
