package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.Routine
import com.threegap.bitnagil.domain.writeroutine.repository.WriteRoutineRepository
import javax.inject.Inject

class GetRoutineUseCase @Inject constructor(
    private val routineRepository: WriteRoutineRepository
) {
    suspend operator fun invoke(
        routineId: String
    ): Result<Routine>{
        return routineRepository.getRoutine(routineId)
    }

}
