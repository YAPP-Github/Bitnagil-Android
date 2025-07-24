package com.threegap.bitnagil.data.routine.repositoryImpl

import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.mapper.toDomain
import com.threegap.bitnagil.domain.routine.model.Routines
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineRemoteDataSource: RoutineRemoteDataSource,
) : RoutineRepository {
    override suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<Routines> =
        routineRemoteDataSource.fetchWeeklyRoutines(startDate, endDate)
            .map { it.toDomain() }
}
