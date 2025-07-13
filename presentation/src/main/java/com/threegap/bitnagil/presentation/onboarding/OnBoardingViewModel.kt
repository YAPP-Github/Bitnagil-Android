package com.threegap.bitnagil.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingAbstractUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingsUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetRecommendOnBoardingRoutinesUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.RegisterRecommendOnBoardingRoutinesUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingAbstractTextItem
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingIntent
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingSideEffect
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOnBoardingsUseCase: GetOnBoardingsUseCase,
    private val getRecommendOnBoardingRoutinesUseCase: GetRecommendOnBoardingRoutinesUseCase,
    private val getOnBoardingAbstractUseCase: GetOnBoardingAbstractUseCase,
    private val registerRecommendOnBoardingRoutinesUseCase: RegisterRecommendOnBoardingRoutinesUseCase,
) : MviViewModel<OnBoardingState, OnBoardingSideEffect, OnBoardingIntent>(
    initState = OnBoardingState.Loading,
    savedStateHandle = savedStateHandle,
) {
    // 내부에 전체 온보딩 항목 저장
    private val onBoardingPageInfos = mutableListOf<OnBoardingPageInfo.SelectOnBoarding>()

    private var loadRecommendRoutinesJob: Job? = null

    init {
        loadOnBoardingItems()
    }

    private fun loadOnBoardingItems() {
        viewModelScope.launch {
            val onBoardings = getOnBoardingsUseCase()

            val onBoardingPages = onBoardings.map { onBoarding ->
                OnBoardingPageInfo.SelectOnBoarding.fromOnBoarding(onBoarding = onBoarding)
            }

            sendIntent(intent = OnBoardingIntent.LoadOnBoardingSuccess(onBoardingPageInfos = onBoardingPages))
        }
    }

    override suspend fun SimpleSyntax<OnBoardingState, OnBoardingSideEffect>.reduceState(
        intent: OnBoardingIntent,
        state: OnBoardingState,
    ): OnBoardingState? {
        when (intent) {
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
                if (isLastSelectOnBoarding) return null

                val nextOnBoardingPageInfo = onBoardingPageInfos[currentState.currentStep]
                val nextButtonEnable = nextOnBoardingPageInfo.isItemSelected
                return currentState.copy(
                    currentOnBoardingPageInfo = nextOnBoardingPageInfo,
                    nextButtonEnable = nextButtonEnable,
                    currentStep = currentState.currentStep + 1,
                )
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

                val recommendRoutinePageInfo = OnBoardingPageInfo.RecommendRoutines(routines = intent.routines)
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

            is OnBoardingIntent.LoadOnBoardingAbstractSuccess -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle) return null

                return currentState.copy(
                    currentOnBoardingPageInfo = intent.onBoardingAbstract,
                    currentStep = currentState.currentStep + 1,
                )
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
            val currentState = stateFlow.value
            if (currentState !is OnBoardingState.Idle) return@launch

            val isLastSelectOnBoarding = currentState.currentStep >= onBoardingPageInfos.size
            if (isLastSelectOnBoarding) {
                val selectedItemIdsWithOnBoardingId = getSelectedOnBoardingItemIdsWithId(onBoardingPageInfos)

                val onBoardingAbstract = getOnBoardingAbstractUseCase(selectedItemIdsWithOnBoardingId = selectedItemIdsWithOnBoardingId)

                val abstractPagePrefixText = onBoardingAbstract.prefix
                val abstractTexts = onBoardingAbstract.abstractTexts.map { onBoardingAbstractText ->
                    onBoardingAbstractText.textItems.map { onBoardingAbstractTextItem ->
                        OnBoardingAbstractTextItem.fromOnBoardingAbstractTextItem(onBoardingAbstractTextItem)
                    }
                }

                sendIntent(
                    intent = OnBoardingIntent.LoadOnBoardingAbstractSuccess(
                        onBoardingAbstract = OnBoardingPageInfo.Abstract(
                            prefix = abstractPagePrefixText,
                            abstractTexts = abstractTexts,
                        ),
                    ),
                )
            } else {
                sendIntent(intent = OnBoardingIntent.SelectNext)
            }
        }
    }

    private fun getSelectedOnBoardingItemIdsWithId(onboardingPageInfos: List<OnBoardingPageInfo.SelectOnBoarding>): List<Pair<String, List<String>>> {
        return onboardingPageInfos.map { onBoardingPage ->
            val id = onBoardingPage.id
            val selectedItemIds = onBoardingPage.items.filter { onBoardingItem ->
                onBoardingItem.selectedIndex != null
            }.map { onBoardingItem ->
                onBoardingItem.id
            }
            Pair(id, selectedItemIds)
        }
    }

    fun selectPrevious() {
        viewModelScope.launch {
            sendIntent(intent = OnBoardingIntent.SelectPrevious)
        }
    }

    fun loadRecommendRoutines() {
        loadRecommendRoutinesJob = viewModelScope.async {
            val minimumDelayDeferred = async {
                delay(2000L)
            }

            val selectedItems = onBoardingPageInfos
                .map { onBoardingPage ->
                    val id = onBoardingPage.id
                    val selectedItemIds = onBoardingPage.items.filter { onBoardingItem ->
                        onBoardingItem.selectedIndex != null
                    }.sortedBy { onBoardingItem ->
                        onBoardingItem.selectedIndex
                    }.map { onBoardingItem ->
                        onBoardingItem.id
                    }
                    Pair(id, selectedItemIds)
                }

            getRecommendOnBoardingRoutinesUseCase(selectedItems).fold(
                onSuccess = { recommendRoutines ->
                    minimumDelayDeferred.await()
                    if (isActive) {
                        sendIntent(
                            intent = OnBoardingIntent.LoadRecommendRoutinesSuccess(
                                routines = recommendRoutines.map {
                                    OnBoardingItem.fromOnBoardingRecommendRoutine(it)
                                },
                            ),
                        )
                    }
                },
                onFailure = {
                },
            )
        }
    }

    fun cancelLoadRecommendRoutines() {
        loadRecommendRoutinesJob?.cancel()
    }

    fun selectRoutine(routineId: String) {
        viewModelScope.launch {
            sendIntent(intent = OnBoardingIntent.SelectRoutine(routineId = routineId))
        }
    }

    fun registerRecommendRoutines() {
        viewModelScope.launch {
            val currentState = stateFlow.value
            if (currentState !is OnBoardingState.Idle) return@launch

            val currentPageInfo = currentState.currentOnBoardingPageInfo
            if (currentPageInfo !is OnBoardingPageInfo.RecommendRoutines) return@launch

            val selectedRoutineIds = currentPageInfo.routines.filter { routineItem ->
                routineItem.selectedIndex != null
            }.map {
                it.id
            }

            registerRecommendOnBoardingRoutinesUseCase(selectedRecommendRoutineIds = selectedRoutineIds).fold(
                onSuccess = { _ ->
                    sendIntent(intent = OnBoardingIntent.NavigateToHome)
                },
                onFailure = {
                },
            )
        }
    }
}
