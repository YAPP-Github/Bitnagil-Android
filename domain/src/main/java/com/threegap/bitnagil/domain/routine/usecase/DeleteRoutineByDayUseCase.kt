package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.RoutineByDayDeletion
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class DeleteRoutineByDayUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(routineByDayDeletion: RoutineByDayDeletion) =
        routineRepository.deleteRoutineByDay(routineByDayDeletion)
}
