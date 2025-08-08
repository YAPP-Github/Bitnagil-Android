package com.threegap.bitnagil.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilProgressTopBar
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.onboarding.component.template.OnBoardingAbstractTemplate
import com.threegap.bitnagil.presentation.onboarding.component.template.OnBoardingSelectTemplate
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingSetType
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingSideEffect
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingState

@Composable
fun OnBoardingScreenContainer(
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToBack: () -> Unit,
) {
    val state by onBoardingViewModel.stateFlow.collectAsState()

    onBoardingViewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            OnBoardingSideEffect.MoveToPreviousScreen -> {
                navigateToBack()
            }
            OnBoardingSideEffect.NavigateToHomeScreen -> {
                navigateToHome()
            }
        }
    }

    OnBoardingScreen(
        state = state,
        onClickNext = onBoardingViewModel::selectNext,
        onClickPreviousInSelectOnBoarding = onBoardingViewModel::selectPrevious,
        onClickItem = onBoardingViewModel::selectItem,
        onClickRoutine = onBoardingViewModel::selectRoutine,
        loadRecommendRoutines = onBoardingViewModel::loadRecommendRoutines,
        onClickRegister = onBoardingViewModel::registerRecommendRoutines,
        cancelRecommendRoutines = onBoardingViewModel::cancelLoadRecommendRoutines,
        onClickSkip = onBoardingViewModel::skipRegisterRecommendRoutines,
    )
}

@Composable
private fun OnBoardingScreen(
    state: OnBoardingState,
    onClickNext: () -> Unit,
    onClickPreviousInSelectOnBoarding: () -> Unit,
    onClickItem: (String) -> Unit,
    onClickRoutine: (String) -> Unit,
    loadRecommendRoutines: () -> Unit,
    cancelRecommendRoutines: () -> Unit,
    onClickRegister: () -> Unit,
    onClickSkip: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(BitnagilTheme.colors.coolGray99)
            .statusBarsPadding(),
    ) {
        BitnagilProgressTopBar(
            onBackClick = onClickPreviousInSelectOnBoarding,
            progress = state.progress,
        )

        when (state) {
            is OnBoardingState.Idle -> {
                when (val currentOnBoardingPageInfo = state.currentOnBoardingPageInfo) {
                    is OnBoardingPageInfo.Abstract -> {
                        OnBoardingAbstractTemplate(
                            modifier = Modifier.weight(1f),
                            title = "이제 당신에게\n꼭 맞는 루틴을 제안해드릴게요.",
                            onInit = loadRecommendRoutines,
                            onBoardingAbstractTexts = currentOnBoardingPageInfo.abstractTexts,
                            onDispose = cancelRecommendRoutines,
                        )
                    }
                    is OnBoardingPageInfo.RecommendRoutines -> {
                        OnBoardingSelectTemplate(
                            modifier = Modifier.weight(1f),
                            title = "당신만의 추천 루틴이\n생성되었어요!",
                            subText = state.onBoardingSetType.subText,
                            items = currentOnBoardingPageInfo.routines,
                            nextButtonEnable = state.nextButtonEnable,
                            onClickNextButton = onClickRegister,
                            onClickItem = if (state.onBoardingSetType.canSelectRoutine) {
                                onClickRoutine
                            } else {
                                null
                            },
                            onClickSkip = if (state.onBoardingSetType.canSkip) {
                                onClickSkip
                            } else {
                                null
                            },
                        )
                    }
                    is OnBoardingPageInfo.SelectOnBoarding -> {
                        OnBoardingSelectTemplate(
                            modifier = Modifier.weight(1f),
                            title = currentOnBoardingPageInfo.title,
                            subText = currentOnBoardingPageInfo.description,
                            items = currentOnBoardingPageInfo.items,
                            nextButtonEnable = state.nextButtonEnable,
                            onClickNextButton = onClickNext,
                            onClickItem = onClickItem,
                        )
                    }
                }
            }
            OnBoardingState.Loading -> {
                Box(modifier = Modifier)
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(
        state = OnBoardingState.Idle(
            nextButtonEnable = false,
            currentOnBoardingPageInfo = OnBoardingPageInfo.RecommendRoutines(
                listOf(
                    OnBoardingItem("1", "루틴명", "세부 루틴 한 줄 설명", null),
                ),
            ),
            totalStep = 5,
            currentStep = 1,
            onBoardingSetType = OnBoardingSetType.RESET,
        ),
        onClickNext = {},
        onClickPreviousInSelectOnBoarding = {},
        onClickItem = {},
        onClickRoutine = {},
        loadRecommendRoutines = {},
        onClickRegister = {},
        cancelRecommendRoutines = {},
        onClickSkip = {},
    )
}
