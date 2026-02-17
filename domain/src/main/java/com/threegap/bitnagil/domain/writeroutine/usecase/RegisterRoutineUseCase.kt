package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.writeroutine.model.RepeatDay
import com.threegap.bitnagil.domain.writeroutine.repository.WriteRoutineRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class RegisterRoutineUseCase @Inject constructor(
    private val writeRoutineRepository: WriteRoutineRepository,
) {
    suspend operator fun invoke(
        name: String,
        repeatDay: List<RepeatDay>,
        startTime: LocalTime,
        startDate: LocalDate,
        endDate: LocalDate,
        subRoutines: List<String>,
        recommendedRoutineType: RecommendCategory?,
    ): Result<Unit> {
        return writeRoutineRepository.registerRoutine(
            name = name,
            repeatDay = repeatDay,
            startTime = startTime,
            startDate = startDate,
            endDate = endDate,
            subRoutines = subRoutines,
            recommendedRoutineType = recommendedRoutineType,
        )
    }
}
