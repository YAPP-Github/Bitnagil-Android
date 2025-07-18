package com.threegap.bitnagil.presentation.recommendroutine.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.recommendroutine.type.RecommendRoutineCategory
import com.threegap.bitnagil.presentation.recommendroutine.type.RecommendRoutineDifficulty
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendRoutineState(
    val isLoading: Boolean = false,
    val showDifficultyBottomSheet: Boolean = false,
    val recommendRoutines: Map<RecommendRoutineCategory, List<RecommendRoutine>> = emptyMap(),
    val selectedCategory: RecommendRoutineCategory = RecommendRoutineCategory.CUSTOM_RECOMMEND,
    val selectedDifficulty: RecommendRoutineDifficulty? = null,
) : MviState {
    val isDefaultCategory: Boolean
        get() = selectedCategory == RecommendRoutineCategory.CUSTOM_RECOMMEND

    val currentRoutines: List<RecommendRoutine>
        get() {
            val routines = recommendRoutines[selectedCategory] ?: emptyList()
            return if (selectedDifficulty != null) {
                routines.filter { it.difficulty == selectedDifficulty }
            } else {
                routines
            }
        }
}
