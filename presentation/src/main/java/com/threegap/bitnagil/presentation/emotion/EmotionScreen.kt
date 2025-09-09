package com.threegap.bitnagil.presentation.emotion

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.presentation.common.dimension.pxToDp
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.common.toast.GlobalBitnagilToast
import com.threegap.bitnagil.presentation.emotion.component.template.EmotionRecommendRoutineScreen
import com.threegap.bitnagil.presentation.emotion.component.template.SimpleEmotionSelectionScreen
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
            is EmotionSideEffect.ShowToast -> GlobalBitnagilToast.showWarning(sideEffect.message)
        }
    }

    when (state.step) {
        EmotionScreenStep.Emotion -> BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val height = constraints.maxHeight.pxToDp()

            if (height > 600.dp) {
                SwipeEmotionSelectionScreen(
                    state = state,
                    onClickPreviousButton = navigateToBack,
                    onSelectEmotion = remember {
                        { emotionType ->
                            viewModel.selectEmotion(emotionType = emotionType, minimumDelay = 1000)
                        }
                    },
                )
            } else {
                SimpleEmotionSelectionScreen(
                    state = state,
                    onClickPreviousButton = navigateToBack,
                    onClickEmotion = viewModel::selectEmotion,
                )
            }
        }
        EmotionScreenStep.RecommendRoutines -> EmotionRecommendRoutineScreen(
            state = state,
            onClickRoutine = viewModel::selectRecommendRoutine,
            onClickRegisterRecommendRoutines = viewModel::registerRecommendRoutines,
            onClickSkip = navigateToBack,
        )
    }
}
