package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.WriteRoutineEvent
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWriteRoutineEventFlowUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(): Flow<WriteRoutineEvent> = routineRepository.getWriteRoutineEventFlow()
}
