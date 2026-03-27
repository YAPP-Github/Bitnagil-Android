package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRoutineSyncErrorUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    operator fun invoke(): Flow<Unit> = routineRepository.syncError
}
