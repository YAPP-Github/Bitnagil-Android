package com.threegap.bitnagil.presentation.onboarding

import OnBoardingProgressBar
import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.presentation.onboarding.component.atom.iconbutton.IconButton
import com.threegap.bitnagil.presentation.onboarding.component.template.OnBoardingAbstractTemplate
import com.threegap.bitnagil.presentation.onboarding.component.template.OnBoardingSelectTemplate
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingState

@Composable
fun OnBoardingScreenContainer(
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val state by onBoardingViewModel.stateFlow.collectAsState()

    OnBoardingScreen(
        state = state,
        onClickNext = onBoardingViewModel::selectNext,
        onClickPreviousInSelectOnBoarding = onBoardingViewModel::selectPrevious,
        onClickItem = onBoardingViewModel::selectItem,
        onClickRoutine = onBoardingViewModel::selectRoutine,
        loadRecommendRoutines = onBoardingViewModel::loadRecommendRoutines,
        onClickRegister = onBoardingViewModel::registerRecommendRoutines,
        cancelRecommendRoutines = onBoardingViewModel::cancelLoadRecommendRoutines,
    )
}

@Composable
fun OnBoardingScreen(
    state: OnBoardingState,
    onClickNext: () -> Unit,
    onClickPreviousInSelectOnBoarding: () -> Unit,
    onClickItem: (String) -> Unit,
    onClickRoutine: (String) -> Unit,
    loadRecommendRoutines: () -> Unit,
    cancelRecommendRoutines: () -> Unit,
    onClickRegister: () -> Unit,
) {
    Column(
        modifier = Modifier.background(color = Color(0xFFF7F7F8)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.padding(start = 4.dp, top = 9.dp, bottom = 9.dp),
                svgResourceId = R.drawable.ic_delete,
                onClick = onClickPreviousInSelectOnBoarding,
            )

            OnBoardingProgressBar(
                modifier = Modifier.padding(start = 12.dp, end = 18.5.dp),
                progress = state.progress,
            )
        }

        when (state) {
            is OnBoardingState.Idle -> {
                when (val currentOnBoardingPageInfo = state.currentOnBoardingPageInfo) {
                    is OnBoardingPageInfo.Abstract -> {
                        OnBoardingAbstractTemplate(
                            modifier = Modifier.weight(1f),
                            title = "이제 당신에게\n꼭 맞는 루틴을 제안해드릴게요.",
                            moveToNext = loadRecommendRoutines,
                            onBoardingAbstractTexts = currentOnBoardingPageInfo.abstractTexts,
                            onDispose = cancelRecommendRoutines,
                        )
                    }
                    is OnBoardingPageInfo.RecommendRoutines -> {
                        OnBoardingSelectTemplate(
                            modifier = Modifier.weight(1f),
                            title = "당신만의 추천 루틴이\n생성되었어요!",
                            subText = "당신의 생활 패턴과 목표에 맞춰 구성된 맞춤 루틴이에요.\n지금부터 가볍게 시작해보세요.",
                            items = currentOnBoardingPageInfo.routines,
                            nextButtonEnable = state.nextButtonEnable,
                            onClickNextButton = onClickRegister,
                            onClickItem = onClickRoutine,
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
            currentOnBoardingPageInfo = OnBoardingPageInfo.SelectOnBoarding(id = "id", title = "title", description = "description"),
            totalStep = 5,
            currentStep = 1,
        ),
        onClickNext = {},
        onClickPreviousInSelectOnBoarding = {},
        onClickItem = {},
        onClickRoutine = {},
        loadRecommendRoutines = {},
        onClickRegister = {},
        cancelRecommendRoutines = {},
    )
}
