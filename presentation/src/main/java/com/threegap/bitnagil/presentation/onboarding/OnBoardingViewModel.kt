package com.threegap.bitnagil.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingAbstractTextListUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingListUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetRecommendOnBoardingRoutineListUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingAbstractTextItem
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingIntent
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingSideEffect
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOnBoardingListUseCase: GetOnBoardingListUseCase,
    private val getRecommendOnBoardingRoutineListUseCase: GetRecommendOnBoardingRoutineListUseCase,
    private val getOnBoardingAbstractTextListUseCase: GetOnBoardingAbstractTextListUseCase,
) : MviViewModel<OnBoardingState, OnBoardingSideEffect, OnBoardingIntent>(
    initState = OnBoardingState.Loading,
    savedStateHandle = savedStateHandle,
) {
    // 내부에 전체 온보딩 항목 저장
    private val onBoardingPageInfos = mutableListOf<OnBoardingPageInfo.SelectOnBoarding>()

    init {
        loadOnBoardingItems()
    }

    private fun loadOnBoardingItems() {
        viewModelScope.launch {
            val onBoardings = getOnBoardingListUseCase()

            val onBoardingPageList = onBoardings.map { onBoarding ->
                OnBoardingPageInfo.SelectOnBoarding.fromOnBoarding(onBoarding = onBoarding)
            }

            sendIntent(intent = OnBoardingIntent.LoadOnBoardingSuccess(onBoardingPageInfos = onBoardingPageList))
        }
    }

    override suspend fun SimpleSyntax<OnBoardingState, OnBoardingSideEffect>.reduceState(
        intent: OnBoardingIntent,
        state: OnBoardingState,
    ): OnBoardingState? {
        when(intent) {
            is OnBoardingIntent.LoadOnBoardingSuccess -> {
                onBoardingPageInfos.clear()
                onBoardingPageInfos.addAll(intent.onBoardingPageInfos)

                return OnBoardingState.Idle(
                    nextButtonEnable = false,
                    currentOnBoardingPageInfo = onBoardingPageInfos.first(),
                    totalStep = onBoardingPageInfos.size + 2,
                    currentStep = 1,
                )
            }

            is OnBoardingIntent.SelectItem -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle) return null

                val currentPageInfo = currentState.currentOnBoardingPageInfo
                if (currentPageInfo !is OnBoardingPageInfo.SelectOnBoarding) return null

                val selectChangedCurrentPageInfo = currentPageInfo.selectItem(itemId = intent.itemId)
                onBoardingPageInfos[currentState.currentStep - 1] = selectChangedCurrentPageInfo
                return currentState.copy(
                    currentOnBoardingPageInfo = selectChangedCurrentPageInfo,
                    nextButtonEnable = selectChangedCurrentPageInfo.isItemSelected,
                )
            }

            is OnBoardingIntent.SelectNext -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle) return null

                val isLastSelectOnBoarding = currentState.currentStep >= onBoardingPageInfos.size
                if (isLastSelectOnBoarding) {
                    val selectedItems = onBoardingPageInfos
                        .map { onBoardingPage ->
                            onBoardingPage.items.filter { onBoardingItem ->
                                onBoardingItem.selected
                            }.map { onBoardingItem ->
                                onBoardingItem.id
                            }
                        }

                    val abstractTextList = getOnBoardingAbstractTextListUseCase(selectedOnBoardingItemIdLists = selectedItems)

                    val abstractPageInfo = OnBoardingPageInfo.Abstract(
                        abstractTextList = abstractTextList.map { onBoardingAbstractText ->
                            onBoardingAbstractText.textItemList.map { onBoardingAbstractTextItem ->
                                OnBoardingAbstractTextItem.fromOnBoardingAbstractTextItem(onBoardingAbstractTextItem)
                            }
                        }
                    )
                    return currentState.copy(
                        currentOnBoardingPageInfo = abstractPageInfo,
                        currentStep = currentState.currentStep + 1,
                    )
                } else {
                    val nextOnBoardingPageInfo = onBoardingPageInfos[currentState.currentStep]
                    val nextButtonEnable = nextOnBoardingPageInfo.isItemSelected
                    return currentState.copy(
                        currentOnBoardingPageInfo = nextOnBoardingPageInfo,
                        nextButtonEnable = nextButtonEnable,
                        currentStep = currentState.currentStep + 1,
                    )
                }
            }

            is OnBoardingIntent.SelectPrevious -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle || currentState.currentStep == 1) {
                    sendSideEffect(sideEffect = OnBoardingSideEffect.MoveToPreviousScreen)
                    return null
                }

                val isSelectOnBoardingStep = currentState.currentStep <= onBoardingPageInfos.size
                if (isSelectOnBoardingStep) {
                    val previousOnBoardingPageInfo = onBoardingPageInfos[currentState.currentStep - 2]
                    val nextButtonEnable = previousOnBoardingPageInfo.isItemSelected
                    return currentState.copy(
                        currentOnBoardingPageInfo = previousOnBoardingPageInfo,
                        nextButtonEnable = nextButtonEnable,
                        currentStep = currentState.currentStep - 1,
                    )
                } else {
                    val selectOnBoardingPageInfo = onBoardingPageInfos.last()
                    val nextButtonEnable = selectOnBoardingPageInfo.isItemSelected
                    return currentState.copy(
                        currentOnBoardingPageInfo = selectOnBoardingPageInfo,
                        nextButtonEnable = nextButtonEnable,
                        currentStep = onBoardingPageInfos.size,
                    )
                }
            }

            is OnBoardingIntent.LoadRecommendRoutinesSuccess -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle) return null

                val recommendRoutinePageInfo = OnBoardingPageInfo.RecommendRoutines(routineList = intent.routineList)
                return currentState.copy(
                    currentOnBoardingPageInfo = recommendRoutinePageInfo,
                    currentStep = currentState.currentStep + 1,
                    nextButtonEnable = false,
                )
            }

            is OnBoardingIntent.SelectRoutine -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle) return null

                val currentPageInfo = currentState.currentOnBoardingPageInfo
                if (currentPageInfo !is OnBoardingPageInfo.RecommendRoutines) return null

                val selectChangedCurrentPageInfo = currentPageInfo.selectItem(itemId = intent.routineId)
                return currentState.copy(
                    currentOnBoardingPageInfo = selectChangedCurrentPageInfo,
                    nextButtonEnable = selectChangedCurrentPageInfo.isItemSelected,
                )
            }

            OnBoardingIntent.NavigateToHome -> {
                sendSideEffect(sideEffect = OnBoardingSideEffect.NavigateToHomeScreen)
                return null
            }
        }
    }

    fun selectItem(itemId: String) {
        viewModelScope.launch {
            sendIntent(intent = OnBoardingIntent.SelectItem(itemId = itemId))
        }
    }

    fun selectNext() {
        viewModelScope.launch {
            sendIntent(intent = OnBoardingIntent.SelectNext)
        }
    }

    fun selectPrevious() {
        viewModelScope.launch {
            sendIntent(intent = OnBoardingIntent.SelectPrevious)
        }
    }

    fun loadRecommendRoutines() {
        viewModelScope.launch {
            val recommendRoutines = getRecommendOnBoardingRoutineListUseCase()

            sendIntent(
                intent = OnBoardingIntent.LoadRecommendRoutinesSuccess(
                    routineList = recommendRoutines.map {
                        OnBoardingItem.fromOnBoardingItem(it)
                    },
                ),
            )
        }
    }

    fun selectRoutine(routineId: String) {
        viewModelScope.launch {
            sendIntent(intent = OnBoardingIntent.SelectRoutine(routineId = routineId))
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            sendIntent(intent = OnBoardingIntent.NavigateToHome)
        }
    }
}
