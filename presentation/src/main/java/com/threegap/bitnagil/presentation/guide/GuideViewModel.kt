package com.threegap.bitnagil.presentation.guide

import androidx.lifecycle.SavedStateHandle
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.guide.model.GuideIntent
import com.threegap.bitnagil.presentation.guide.model.GuideSideEffect
import com.threegap.bitnagil.presentation.guide.model.GuideState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class GuideViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : MviViewModel<GuideState, GuideSideEffect, GuideIntent>(
    initState = GuideState(),
    savedStateHandle = savedStateHandle,
) {
    override suspend fun SimpleSyntax<GuideState, GuideSideEffect>.reduceState(
        intent: GuideIntent,
        state: GuideState,
    ): GuideState? {
        val newState = when (intent) {
            is GuideIntent.OnClickGuideButton -> {
                state.copy(
                    guideType = intent.guideType,
                    guideBottomSheetVisible = true,
                )
            }

            is GuideIntent.OnHideGuideBottomSheet -> {
                state.copy(
                    guideType = null,
                    guideBottomSheetVisible = false,
                )
            }

            is GuideIntent.OnBackClick -> {
                sendSideEffect(GuideSideEffect.NavigateToBack)
                null
            }
        }

        return newState
    }
}
