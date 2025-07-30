package com.threegap.bitnagil.data.routine.repositoryImpl

import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.mapper.toDomain
import com.threegap.bitnagil.data.routine.mapper.toDto
import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.data.routine.model.request.toDto
import com.threegap.bitnagil.domain.routine.model.RoutineByDayDeletion
import com.threegap.bitnagil.domain.routine.model.RoutineCompletion
import com.threegap.bitnagil.domain.routine.model.Routines
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineRemoteDataSource: RoutineRemoteDataSource,
) : RoutineRepository {
    override suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<Routines> =
        routineRemoteDataSource.fetchWeeklyRoutines(startDate, endDate)
            .map { it.toDomain() }

    override suspend fun syncRoutineCompletion(routineCompletion: RoutineCompletion): Result<Unit> =
        routineRemoteDataSource.syncRoutineCompletion(routineCompletion.toDto())

    override suspend fun deleteRoutine(routineId: String): Result<Unit> =
        routineRemoteDataSource.deleteRoutine(routineId)

    override suspend fun getRoutine(routineId: String): Result<Routine> =
        routineRemoteDataSource.getRoutine(routineId).map { it.toDomain() }

    override suspend fun deleteRoutineByDay(routineByDayDeletion: RoutineByDayDeletion): Result<Unit> =
        routineRemoteDataSource.deleteRoutineByDay(routineByDayDeletion.toDto())
}
