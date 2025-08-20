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
import com.threegap.bitnagil.presentation.common.toast.GlobalBitnagilToast
import com.threegap.bitnagil.presentation.onboarding.component.template.OnBoardingAbstractTemplate
import com.threegap.bitnagil.presentation.onboarding.component.template.OnBoardingIntroTemplate
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

            is OnBoardingSideEffect.ShowToast -> {
                GlobalBitnagilToast.showWarning(sideEffect.message)
            }
        }
    }

    OnBoardingScreen(
        state = state,
        onClickNext = onBoardingViewModel::selectNext,
        onClickLoadOnBoarding = onBoardingViewModel::loadOnBoardingItems,
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
    onClickLoadOnBoarding: () -> Unit,
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
            .background(BitnagilTheme.colors.white)
            .statusBarsPadding(),
    ) {
        if (state.showProgress)
            BitnagilProgressTopBar(
                modifier = Modifier,
                onBackClick = onClickPreviousInSelectOnBoarding,
                progress = state.progress,
            )

        when (state) {
            is OnBoardingState.Idle -> {
                when (val currentOnBoardingPageInfo = state.currentOnBoardingPageInfo) {
                    OnBoardingPageInfo.Intro -> {
                        OnBoardingIntroTemplate(
                            userName = state.userName,
                            onClickNextButton = onClickLoadOnBoarding
                        )
                    }
                    is OnBoardingPageInfo.Abstract -> {
                        OnBoardingAbstractTemplate(
                            modifier = Modifier.weight(1f),
                            title = "이제 포모가 당신에게\n꼭 맞는 루틴을 찾아줄거에요.",
                            onBoardingAbstractTexts = currentOnBoardingPageInfo.abstractTexts,
                            onDispose = cancelRecommendRoutines,
                            onClickNextButton = loadRecommendRoutines,
                            nextButtonEnable = state.nextButtonEnable,
                            userName = state.userName,
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

                    is OnBoardingPageInfo.ExistedOnBoardingAbstract -> {
                        OnBoardingAbstractTemplate(
                            modifier = Modifier.weight(1f),
                            title = "이전에 설정한 목표예요!\n변경하시겠어요?",
                            onBoardingAbstractTexts = currentOnBoardingPageInfo.abstractTexts,
                            onDispose = cancelRecommendRoutines,
                            onClickNextButton = onClickLoadOnBoarding,
                            nextButtonEnable = true,
                            userName = state.userName,
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
            userName = "안드로이드"
        ),
        onClickNext = {},
        onClickLoadOnBoarding = {},
        onClickPreviousInSelectOnBoarding = {},
        onClickItem = {},
        onClickRoutine = {},
        loadRecommendRoutines = {},
        onClickRegister = {},
        cancelRecommendRoutines = {},
        onClickSkip = {},
    )
}
