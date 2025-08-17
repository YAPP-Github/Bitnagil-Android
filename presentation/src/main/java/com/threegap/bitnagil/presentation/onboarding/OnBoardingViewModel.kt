package com.threegap.bitnagil.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingAbstractUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingsUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetRecommendOnBoardingRoutinesUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.RegisterRecommendOnBoardingRoutinesUseCase
import com.threegap.bitnagil.domain.user.usecase.FetchUserProfileUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingAbstractTextItem
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingSetType
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingIntent
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingSideEffect
import com.threegap.bitnagil.presentation.onboarding.model.mvi.OnBoardingState
import com.threegap.bitnagil.presentation.onboarding.model.navarg.OnBoardingScreenArg
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax

@HiltViewModel(assistedFactory = OnBoardingViewModel.Factory::class)
class OnBoardingViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOnBoardingsUseCase: GetOnBoardingsUseCase,
    private val getRecommendOnBoardingRoutinesUseCase: GetRecommendOnBoardingRoutinesUseCase,
    private val getOnBoardingAbstractUseCase: GetOnBoardingAbstractUseCase,
    private val registerRecommendOnBoardingRoutinesUseCase: RegisterRecommendOnBoardingRoutinesUseCase,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    @Assisted private val onBoardingArg: OnBoardingScreenArg,
) : MviViewModel<OnBoardingState, OnBoardingSideEffect, OnBoardingIntent>(
    initState = OnBoardingState.Loading,
    savedStateHandle = savedStateHandle,
) {
    @AssistedFactory interface Factory {
        fun create(onBoardingArg: OnBoardingScreenArg): OnBoardingViewModel
    }

    // 내부에 전체 온보딩 항목 저장
    private val selectOnBoardingPageInfos = mutableListOf<OnBoardingPageInfo.SelectOnBoarding>()

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

            val userProfile = fetchUserProfileUseCase()
            val userName = userProfile.fold(
                onSuccess = {
                    return@fold it.nickname
                },
                onFailure = {
                    return@fold "-"
                },
            )

            sendIntent(intent = OnBoardingIntent.LoadOnBoardingSuccess(onBoardingPageInfos = onBoardingPages, userName = userName))
        }
    }

    override suspend fun SimpleSyntax<OnBoardingState, OnBoardingSideEffect>.reduceState(
        intent: OnBoardingIntent,
        state: OnBoardingState,
    ): OnBoardingState? {
        when (intent) {
            is OnBoardingIntent.LoadOnBoardingSuccess -> {
                selectOnBoardingPageInfos.clear()
                selectOnBoardingPageInfos.addAll(intent.onBoardingPageInfos)

                val onBoardingSetType =  OnBoardingSetType.fromOnBoardingScreenArg(onBoardingArg)
                val firstPage = when(onBoardingSetType) {
                    OnBoardingSetType.NEW -> OnBoardingPageInfo.Intro
                    OnBoardingSetType.RESET -> OnBoardingPageInfo.Intro
                }

                return OnBoardingState.Idle(
                    nextButtonEnable = true,
                    currentOnBoardingPageInfo = firstPage,
                    totalStep = selectOnBoardingPageInfos.size + 2,
                    currentStep = 0,
                    onBoardingSetType = onBoardingSetType,
                    userName = intent.userName
                )
            }

            is OnBoardingIntent.SelectItem -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle) return null

                val currentPageInfo = currentState.currentOnBoardingPageInfo
                if (currentPageInfo !is OnBoardingPageInfo.SelectOnBoarding) return null

                val selectChangedCurrentPageInfo = currentPageInfo.selectItem(itemId = intent.itemId)
                selectOnBoardingPageInfos[currentState.currentStep - 1] = selectChangedCurrentPageInfo
                return currentState.copy(
                    currentOnBoardingPageInfo = selectChangedCurrentPageInfo,
                    nextButtonEnable = selectChangedCurrentPageInfo.isItemSelected,
                )
            }

            is OnBoardingIntent.SelectNext -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle) return null

                val isLastPageOfSelectOnBoarding = currentState.currentStep >= selectOnBoardingPageInfos.size
                if (isLastPageOfSelectOnBoarding) return null

                val nextOnBoardingPageInfo = selectOnBoardingPageInfos[currentState.currentStep]
                val nextButtonEnable = nextOnBoardingPageInfo.isItemSelected
                return currentState.copy(
                    currentOnBoardingPageInfo = nextOnBoardingPageInfo,
                    nextButtonEnable = nextButtonEnable,
                    currentStep = currentState.currentStep + 1,
                )
            }

            is OnBoardingIntent.SelectPrevious -> {
                val currentState = state
                if (currentState !is OnBoardingState.Idle || currentState.currentStep == 0) {
                    sendSideEffect(sideEffect = OnBoardingSideEffect.MoveToPreviousScreen)
                    return null
                }

                if (currentState.currentStep == 1) {
                    return currentState.copy(
                        currentStep = 0,
                        currentOnBoardingPageInfo = OnBoardingPageInfo.Intro,
                        nextButtonEnable = true
                    )
                }

                val isSelectOnBoardingStep = currentState.currentStep <= selectOnBoardingPageInfos.size
                if (isSelectOnBoardingStep) {
                    val previousOnBoardingPageInfo = selectOnBoardingPageInfos[currentState.currentStep - 2]
                    val nextButtonEnable = previousOnBoardingPageInfo.isItemSelected
                    return currentState.copy(
                        currentOnBoardingPageInfo = previousOnBoardingPageInfo,
                        nextButtonEnable = nextButtonEnable,
                        currentStep = currentState.currentStep - 1,
                    )
                } else {
                    val selectOnBoardingPageInfo = selectOnBoardingPageInfos.last()
                    val nextButtonEnable = selectOnBoardingPageInfo.isItemSelected
                    return currentState.copy(
                        currentOnBoardingPageInfo = selectOnBoardingPageInfo,
                        nextButtonEnable = nextButtonEnable,
                        currentStep = selectOnBoardingPageInfos.size,
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
                    nextButtonEnable = !currentState.onBoardingSetType.canSelectRoutine,
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

            val isLastSelectOnBoarding = currentState.currentStep >= selectOnBoardingPageInfos.size
            if (isLastSelectOnBoarding) {
                val selectedItemIdsWithOnBoardingId = getSelectedOnBoardingItemIdsWithId(selectOnBoardingPageInfos)

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
        loadRecommendRoutinesJob?.cancel()
        loadRecommendRoutinesJob = viewModelScope.async {
            val selectedItems = selectOnBoardingPageInfos
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

    fun skipRegisterRecommendRoutines() {
        viewModelScope.launch {
            sendIntent(intent = OnBoardingIntent.NavigateToHome)
        }
    }
}
