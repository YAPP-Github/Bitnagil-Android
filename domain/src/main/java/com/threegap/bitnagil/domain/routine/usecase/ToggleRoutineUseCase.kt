package com.threegap.bitnagil.domain.routine.usecase

import com.threegap.bitnagil.domain.routine.model.RoutineToggleState
import javax.inject.Inject

class ToggleRoutineUseCase @Inject constructor() {

    /**
     * 메인 루틴을 토글합니다.
     * 메인 루틴이 토글되면 모든 서브 루틴도 같은 상태로 변경됩니다.
     *
     * @param isCompleted 현재 메인 루틴 완료 상태
     * @param subRoutineStates 현재 서브 루틴 완료 상태 리스트
     * @return 토글된 루틴 상태
     */
    fun toggleMainRoutine(
        isCompleted: Boolean,
        subRoutineStates: List<Boolean>,
    ): RoutineToggleState {
        val newIsCompleted = !isCompleted
        val newSubRoutineStates = subRoutineStates.map { newIsCompleted }

        return RoutineToggleState(
            subRoutinesIsCompleted = newSubRoutineStates,
        )
    }

    /**
     * 특정 서브 루틴을 토글합니다.
     * 모든 서브 루틴이 완료되면 메인 루틴도 자동으로 완료됩니다.
     *
     * @param index 토글할 서브 루틴의 인덱스
     * @param subRoutineStates 현재 서브 루틴 완료 상태 리스트
     * @return 토글된 루틴 상태, 잘못된 인덱스인 경우 null
     */
    fun toggleSubRoutine(
        index: Int,
        subRoutineStates: List<Boolean>,
    ): RoutineToggleState? {
        if (index !in subRoutineStates.indices) {
            return null
        }

        val newState = !subRoutineStates[index]
        val newSubRoutineStates = subRoutineStates.toMutableList().apply {
            this[index] = newState
        }

        return RoutineToggleState(
            subRoutinesIsCompleted = newSubRoutineStates,
        )
    }
}
