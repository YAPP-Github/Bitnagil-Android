package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.WriteRoutineEvent
import com.threegap.bitnagil.domain.writeroutine.repository.WriteRoutineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWriteRoutineEventFlowUseCase @Inject constructor(
    private val repository: WriteRoutineRepository,
) {
    suspend operator fun invoke(): Flow<WriteRoutineEvent> = repository.getWriteRoutineEventFlow()
}
