package com.threegap.bitnagil.data.recommendroutine.repositoryImpl

import com.threegap.bitnagil.data.recommendroutine.datasource.RecommendRoutineDataSource
import com.threegap.bitnagil.data.recommendroutine.model.response.toDomain
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutines
import com.threegap.bitnagil.domain.recommendroutine.repository.RecommendRoutineRepository
import javax.inject.Inject

class RecommendRoutineRepositoryImpl @Inject constructor(
    private val recommendRoutineDataSource: RecommendRoutineDataSource,
) : RecommendRoutineRepository {
    override suspend fun fetchRecommendRoutines(): Result<RecommendRoutines> =
        recommendRoutineDataSource.fetchRecommendRoutines()
            .map { it.toDomain() }
}
