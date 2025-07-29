package com.threegap.bitnagil.presentation.recommendroutine.model

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class RecommendRoutineIntent : MviIntent {
    data class UpdateLoading(val isLoading: Boolean) : RecommendRoutineIntent()
    data object LoadRecommendRoutines : RecommendRoutineIntent()
    data class OnCategorySelected(val category: RecommendCategory) : RecommendRoutineIntent()
    data class OnRecommendLevelSelected(val recommendLevel: RecommendLevel?) : RecommendRoutineIntent()
    data object ShowRecommendLevelBottomSheet : RecommendRoutineIntent()
    data object HideRecommendLevelBottomSheet : RecommendRoutineIntent()
    data object ClearRecommendLevelFilter : RecommendRoutineIntent()
}
