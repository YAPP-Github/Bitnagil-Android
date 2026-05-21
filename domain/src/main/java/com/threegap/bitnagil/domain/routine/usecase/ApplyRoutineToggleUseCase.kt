package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.ToggleStrategy
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import javax.inject.Inject

class ApplyRoutineToggleUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val toggleRoutineUseCase: ToggleRoutineUseCase,
) {
    suspend operator fun invoke(
        dateKey: String,
        routineId: String,
        isCompleted: Boolean,
        subRoutineCompletionStates: List<Boolean>,
        strategy: ToggleStrategy,
    ) {
        val toggledState =
            when (strategy) {
                is ToggleStrategy.Main -> {
                    toggleRoutineUseCase.toggleMainRoutine(
                        isCompleted = isCompleted,
                        subRoutineStates = subRoutineCompletionStates,
                    )
                }

                is ToggleStrategy.Sub -> {
                    toggleRoutineUseCase.toggleSubRoutine(
                        index = strategy.index,
                        subRoutineStates = subRoutineCompletionStates,
                    ) ?: return
                }
            }

        val completionInfo = RoutineCompletionInfo(
            routineId = routineId,
            routineCompleteYn = toggledState.isCompleted,
            subRoutineCompleteYn = toggledState.subRoutinesIsCompleted,
        )

        routineRepository.applyRoutineToggle(dateKey, routineId, completionInfo)
    }
}
