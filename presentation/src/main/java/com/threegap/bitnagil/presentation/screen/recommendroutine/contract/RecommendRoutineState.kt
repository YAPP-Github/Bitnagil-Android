package com.threegap.bitnagil.presentation.screen.recommendroutine.contract

import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.presentation.screen.recommendroutine.model.RecommendRoutineUiModel

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
