package com.threegap.bitnagil.presentation.emotion

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.emotion.component.template.EmotionRecommendRoutineScreen
import com.threegap.bitnagil.presentation.emotion.component.template.SwipeEmotionSelectionScreen
import com.threegap.bitnagil.presentation.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionSideEffect

@Composable
fun EmotionScreenContainer(
    viewModel: EmotionViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsState()

    BackHandler {
        viewModel.moveToPrev()
    }

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            EmotionSideEffect.NavigateToBack -> navigateToBack()
        }
    }

    when (state.step) {
        EmotionScreenStep.Emotion -> SwipeEmotionSelectionScreen(
            state = state,
            onClickPreviousButton = navigateToBack,
            onSelectEmotion = viewModel::selectEmotion,
        )
        EmotionScreenStep.RecommendRoutines -> EmotionRecommendRoutineScreen(
            state = state,
            onClickRoutine = viewModel::selectRecommendRoutine,
            onClickRegisterRecommendRoutines = viewModel::registerRecommendRoutines,
            onClickSkip = navigateToBack,
        )
    }
}
