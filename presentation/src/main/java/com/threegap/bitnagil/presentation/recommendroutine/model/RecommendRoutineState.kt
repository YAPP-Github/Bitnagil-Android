package com.threegap.bitnagil.presentation.recommendroutine.model

import com.threegap.bitnagil.domain.recommendroutine.model.EmotionMarbleType
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendRoutineState(
    val isLoading: Boolean = false,
    val currentRoutines: List<RecommendRoutineUiModel> = emptyList(),
    val selectedCategory: RecommendCategory = RecommendCategory.PERSONALIZED,
    val recommendLevelBottomSheetVisible: Boolean = false,
    val selectedRecommendLevel: RecommendLevel? = null,
    val emotionMarbleType: EmotionMarbleType? = null,
) : MviState {
    val isDefaultCategory: Boolean
        get() = selectedCategory == RecommendCategory.PERSONALIZED
}
