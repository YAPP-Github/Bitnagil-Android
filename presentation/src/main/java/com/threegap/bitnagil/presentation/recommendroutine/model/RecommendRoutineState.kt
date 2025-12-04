package com.threegap.bitnagil.presentation.recommendroutine.model

import com.threegap.bitnagil.domain.recommendroutine.model.EmotionMarbleType
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel

data class RecommendRoutineState(
    val isLoading: Boolean,
    val currentRoutines: List<RecommendRoutineUiModel>,
    val selectedCategory: RecommendCategory,
    val recommendLevelBottomSheetVisible: Boolean,
    val selectedRecommendLevel: RecommendLevel?,
    val emotionMarbleType: EmotionMarbleType?,
) {
    val shouldShowEmotionButton: Boolean
        get() = selectedCategory == RecommendCategory.PERSONALIZED && emotionMarbleType == null

    companion object {
        val INIT = RecommendRoutineState(
            isLoading = false,
            currentRoutines = emptyList(),
            selectedCategory = RecommendCategory.PERSONALIZED,
            recommendLevelBottomSheetVisible = false,
            selectedRecommendLevel = null,
            emotionMarbleType = null,
        )
    }
}
