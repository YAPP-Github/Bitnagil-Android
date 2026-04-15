package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.RoutineRegisterInfo
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class RegisterRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
) {
    suspend operator fun invoke(
        name: String,
        repeatDay: List<DayOfWeek>,
        startTime: LocalTime,
        startDate: LocalDate,
        endDate: LocalDate,
        subRoutines: List<String>,
        recommendedRoutineType: RecommendCategory?,
    ): Result<Unit> {
        return routineRepository.registerRoutine(
            routineRegisterInfo = RoutineRegisterInfo(
                name = name,
                repeatDay = repeatDay,
                startTime = startTime,
                startDate = startDate,
                endDate = endDate,
                subRoutines = subRoutines,
                recommendedRoutineType = recommendedRoutineType,
            ),
        )
    }
}
