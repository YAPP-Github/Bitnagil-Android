package com.threegap.bitnagil.presentation.recommendroutine.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import com.threegap.bitnagil.presentation.recommendroutine.type.RecommendRoutineCategory
import com.threegap.bitnagil.presentation.recommendroutine.type.RecommendRoutineDifficulty

sealed class RecommendRoutineIntent : MviIntent {
    data object ShowDifficultyBottomSheet : RecommendRoutineIntent()
    data object HideDifficultyBottomSheet : RecommendRoutineIntent()
    data object ClearDifficultyFilter : RecommendRoutineIntent()
    data class SetLoading(val isLoading: Boolean) : RecommendRoutineIntent()
    data class OnCategorySelected(val category: RecommendRoutineCategory) : RecommendRoutineIntent()
    data class OnDifficultySelected(val difficulty: RecommendRoutineDifficulty?) : RecommendRoutineIntent()
    data class LoadRecommendRoutines(
        val recommendRoutines: Map<RecommendRoutineCategory, List<RecommendRoutine>>,
    ) : RecommendRoutineIntent()
}
