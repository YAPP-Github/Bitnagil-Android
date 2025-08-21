package com.threegap.bitnagil.presentation.recommendroutine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.emotion.usecase.GetEmotionChangeEventFlowUseCase
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.domain.recommendroutine.usecase.FetchRecommendRoutinesUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineIntent
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineSideEffect
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineState
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineUiModel
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutinesUiModel
import com.threegap.bitnagil.presentation.recommendroutine.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class RecommendRoutineViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchRecommendRoutinesUseCase: FetchRecommendRoutinesUseCase,
    private val getEmotionChangeEventFlowUseCase: GetEmotionChangeEventFlowUseCase,
) : MviViewModel<RecommendRoutineState, RecommendRoutineSideEffect, RecommendRoutineIntent>(
    initState = RecommendRoutineState(),
    savedStateHandle = savedStateHandle,
) {

    init {
        loadRecommendRoutines()
        observeEmotionChangeEvent()
    }

    private var recommendRoutines: RecommendRoutinesUiModel = RecommendRoutinesUiModel()

    override suspend fun SimpleSyntax<RecommendRoutineState, RecommendRoutineSideEffect>.reduceState(
        intent: RecommendRoutineIntent,
        state: RecommendRoutineState,
    ): RecommendRoutineState? = when (intent) {
        is RecommendRoutineIntent.UpdateLoading -> {
            state.copy(isLoading = intent.isLoading)
        }

        is RecommendRoutineIntent.LoadRecommendRoutines -> {
            state.copy(
                isLoading = false,
                currentRoutines = getCurrentRoutines(state.selectedCategory, state.selectedRecommendLevel),
                emotionMarbleType = recommendRoutines.emotionMarbleType,
            )
        }

        is RecommendRoutineIntent.OnCategorySelected -> {
            state.copy(
                selectedCategory = intent.category,
                currentRoutines = getCurrentRoutines(intent.category, state.selectedRecommendLevel),
            )
        }

        is RecommendRoutineIntent.ShowRecommendLevelBottomSheet -> {
            state.copy(recommendLevelBottomSheetVisible = true)
        }

        is RecommendRoutineIntent.HideRecommendLevelBottomSheet -> {
            state.copy(recommendLevelBottomSheetVisible = false)
        }

        is RecommendRoutineIntent.OnRecommendLevelSelected -> {
            state.copy(
                selectedRecommendLevel = intent.recommendLevel,
                currentRoutines = getCurrentRoutines(state.selectedCategory, intent.recommendLevel),
            )
        }

        RecommendRoutineIntent.ClearRecommendLevelFilter -> {
            state.copy(
                selectedRecommendLevel = null,
                currentRoutines = getCurrentRoutines(state.selectedCategory, null),
            )
        }
    }

    private fun getCurrentRoutines(
        category: RecommendCategory,
        level: RecommendLevel?,
    ): List<RecommendRoutineUiModel> {
        val routines = recommendRoutines.recommendRoutinesByCategory[category] ?: emptyList()
        return if (level != null) {
            routines.filter { it.level == level }
        } else {
            routines
        }
    }

    private fun observeEmotionChangeEvent() {
        viewModelScope.launch {
            getEmotionChangeEventFlowUseCase().collect {
                loadRecommendRoutines()
            }
        }
    }

    private fun loadRecommendRoutines() {
        sendIntent(RecommendRoutineIntent.UpdateLoading(true))
        viewModelScope.launch {
            fetchRecommendRoutinesUseCase().fold(
                onSuccess = {
                    recommendRoutines = it.toUiModel()
                    sendIntent(RecommendRoutineIntent.LoadRecommendRoutines)
                },
                onFailure = {
                    sendIntent(RecommendRoutineIntent.UpdateLoading(false))
                },
            )
        }
    }
}
