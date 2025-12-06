package com.threegap.bitnagil.presentation.recommendroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.emotion.usecase.GetEmotionChangeEventFlowUseCase
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.domain.recommendroutine.usecase.FetchRecommendRoutinesUseCase
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineSideEffect
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineState
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineUiModel
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutinesUiModel
import com.threegap.bitnagil.presentation.recommendroutine.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecommendRoutineViewModel @Inject constructor(
    private val fetchRecommendRoutinesUseCase: FetchRecommendRoutinesUseCase,
    private val getEmotionChangeEventFlowUseCase: GetEmotionChangeEventFlowUseCase,
) : ContainerHost<RecommendRoutineState, RecommendRoutineSideEffect>, ViewModel() {

    override val container: Container<RecommendRoutineState, RecommendRoutineSideEffect> =
        container(initialState = RecommendRoutineState.INIT)

    init {
        loadRecommendRoutines()
        observeEmotionChangeEvent()
    }

    private var recommendRoutines: RecommendRoutinesUiModel = RecommendRoutinesUiModel()

    fun updateRoutineCategory(category: RecommendCategory) {
        intent {
            reduce {
                state.copy(
                    selectedCategory = category,
                    currentRoutines = getCurrentRoutines(category, state.selectedRecommendLevel),
                )
            }
        }
    }

    fun showRecommendLevelBottomSheet() {
        intent {
            reduce { state.copy(recommendLevelBottomSheetVisible = true) }
        }
    }

    fun hideRecommendLevelBottomSheet() {
        intent {
            reduce { state.copy(recommendLevelBottomSheetVisible = false) }
        }
    }

    fun updateRecommendLevel(recommendLevel: RecommendLevel?) {
        intent {
            reduce {
                state.copy(
                    selectedRecommendLevel = recommendLevel,
                    currentRoutines = getCurrentRoutines(state.selectedCategory, recommendLevel),
                )
            }
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
        intent {
            reduce { state.copy(isLoading = true) }
            fetchRecommendRoutinesUseCase().fold(
                onSuccess = {
                    reduce {
                        recommendRoutines = it.toUiModel()
                        state.copy(
                            isLoading = false,
                            currentRoutines = getCurrentRoutines(state.selectedCategory, state.selectedRecommendLevel),
                            emotionMarbleType = recommendRoutines.emotionMarbleType,
                        )
                    }
                },
                onFailure = {
                    reduce { state.copy(isLoading = false) }
                },
            )
        }
    }

    fun navigateToEmotion() {
        intent {
            postSideEffect(RecommendRoutineSideEffect.NavigateToEmotion)
        }
    }

    fun navigateToRegisterRoutine(routineId: String) {
        intent {
            postSideEffect(RecommendRoutineSideEffect.NavigateToRegisterRoutine(routineId))
        }
    }
}
