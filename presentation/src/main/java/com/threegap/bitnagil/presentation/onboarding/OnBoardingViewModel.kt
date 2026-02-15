package com.threegap.bitnagil.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingAbstractUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetOnBoardingsUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetRecommendOnBoardingRoutinesUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.GetUserOnBoardingUseCase
import com.threegap.bitnagil.domain.onboarding.usecase.RegisterRecommendOnBoardingRoutinesUseCase
import com.threegap.bitnagil.domain.user.usecase.FetchUserProfileUseCase
import com.threegap.bitnagil.presentation.onboarding.contract.OnBoardingSideEffect
import com.threegap.bitnagil.presentation.onboarding.contract.OnBoardingState
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingAbstractTextItemUiModel
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItemUiModel
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingSetType
import com.threegap.bitnagil.presentation.onboarding.model.navarg.OnBoardingScreenArg
import com.threegap.bitnagil.presentation.onboarding.model.toUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel(assistedFactory = OnBoardingViewModel.Factory::class)
class OnBoardingViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOnBoardingsUseCase: GetOnBoardingsUseCase,
    private val getRecommendOnBoardingRoutinesUseCase: GetRecommendOnBoardingRoutinesUseCase,
    private val getOnBoardingAbstractUseCase: GetOnBoardingAbstractUseCase,
    private val registerRecommendOnBoardingRoutinesUseCase: RegisterRecommendOnBoardingRoutinesUseCase,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val getUserOnBoardingUseCase: GetUserOnBoardingUseCase,
    @Assisted private val onBoardingArg: OnBoardingScreenArg,
) : ContainerHost<OnBoardingState, OnBoardingSideEffect>, ViewModel() {
    @AssistedFactory interface Factory {
        fun create(onBoardingArg: OnBoardingScreenArg): OnBoardingViewModel
    }

    override val container: Container<OnBoardingState, OnBoardingSideEffect> = container(
        savedStateHandle = savedStateHandle,
        initialState = OnBoardingState.Loading,
    )

    // 내부에 전체 온보딩 항목 저장
    private val selectOnBoardingPageInfos = mutableListOf<OnBoardingPageInfo.SelectOnBoarding>()
    private var existedOnBoardingAbstract: OnBoardingPageInfo.ExistedOnBoardingAbstract? = null

    private var loadRecommendRoutinesJob: Job? = null

    init {
        loadInitData()
    }

    private fun loadInitData() {
        val onBoardingSetType = OnBoardingSetType.fromOnBoardingScreenArg(onBoardingArg)

        when (onBoardingSetType) {
            OnBoardingSetType.NEW -> {
                loadIntro()
            }
            OnBoardingSetType.RESET -> {
                loadUserOnBoarding()
            }
        }
    }

    private fun loadIntro() = intent {
        val userName = fetchUserProfileUseCase().getOrNull()?.nickname ?: "-"

        reduce {
            OnBoardingState.Idle(
                nextButtonEnable = true,
                currentOnBoardingPageInfo = OnBoardingPageInfo.Intro,
                totalStep = 1,
                currentStep = 0,
                onBoardingSetType = OnBoardingSetType.NEW,
                userName = userName,
            )
        }
    }

    private fun loadUserOnBoarding() = intent {
        val userName = fetchUserProfileUseCase().getOrNull()?.nickname ?: "-"
        val userOnBoarding = getUserOnBoardingUseCase().fold(
            onSuccess = { it },
            onFailure = {
                postSideEffect(OnBoardingSideEffect.MoveToPreviousScreen)
                postSideEffect(OnBoardingSideEffect.ShowToast(message = it.message ?: "에러가 발생했습니다. 잠시 후 시도해주세요."))
                return@intent
            },
        )

        val onBoardingAbstract = getOnBoardingAbstractUseCase(selectedItemIdsWithOnBoardingId = userOnBoarding)

        val abstractPagePrefixText = onBoardingAbstract.prefix
        val abstractTexts = onBoardingAbstract.abstractTexts.map { onBoardingAbstractText ->
            onBoardingAbstractText.textItems.map { it.toUiModel() }
        }

        val existedOnBoardingAbstract = OnBoardingPageInfo.ExistedOnBoardingAbstract(
            prefix = abstractPagePrefixText,
            abstractTexts = abstractTexts,
        )
        this@OnBoardingViewModel.existedOnBoardingAbstract = existedOnBoardingAbstract

        reduce {
            OnBoardingState.Idle(
                nextButtonEnable = true,
                currentOnBoardingPageInfo = existedOnBoardingAbstract,
                totalStep = 1,
                currentStep = 0,
                onBoardingSetType = OnBoardingSetType.RESET,
                userName = userName,
            )
        }
    }

    fun loadOnBoardingItems() = intent {
        val onBoardings = getOnBoardingsUseCase()
        val onBoardingPages = onBoardings.map { it.toUiModel() }

        val currentState = state
        if (currentState !is OnBoardingState.Idle) return@intent

        selectOnBoardingPageInfos.clear()
        selectOnBoardingPageInfos.addAll(onBoardingPages)

        reduce {
            currentState.copy(
                nextButtonEnable = false,
                currentOnBoardingPageInfo = onBoardingPages.first(),
                totalStep = selectOnBoardingPageInfos.size + 2,
                currentStep = 1,
            )
        }
    }

    fun selectItem(itemId: String) = intent {
        val currentState = state
        if (currentState !is OnBoardingState.Idle) return@intent

        val currentPageInfo = currentState.currentOnBoardingPageInfo
        if (currentPageInfo !is OnBoardingPageInfo.SelectOnBoarding) return@intent

        val selectChangedCurrentPageInfo = currentPageInfo.selectItem(itemId = itemId)
        selectOnBoardingPageInfos[currentState.currentStep - 1] = selectChangedCurrentPageInfo

        reduce {
            currentState.copy(
                currentOnBoardingPageInfo = selectChangedCurrentPageInfo,
                nextButtonEnable = selectChangedCurrentPageInfo.isItemSelected,
            )
        }
    }

    fun selectNext() = intent {
        val currentState = state
        if (currentState !is OnBoardingState.Idle) return@intent

        val isLastSelectOnBoarding = currentState.currentStep >= selectOnBoardingPageInfos.size
        if (isLastSelectOnBoarding) {
            val selectedItemIdsWithOnBoardingId = getSelectedOnBoardingItemIdsWithId(selectOnBoardingPageInfos)

            val onBoardingAbstract = getOnBoardingAbstractUseCase(selectedItemIdsWithOnBoardingId = selectedItemIdsWithOnBoardingId)

            val abstractPagePrefixText = onBoardingAbstract.prefix
            val abstractTexts = onBoardingAbstract.abstractTexts.map { onBoardingAbstractText ->
                onBoardingAbstractText.textItems.map { it.toUiModel() }
            }

            reduce {
                currentState.copy(
                    currentOnBoardingPageInfo = OnBoardingPageInfo.Abstract(
                        prefix = abstractPagePrefixText,
                        abstractTexts = abstractTexts,
                    ),
                    currentStep = currentState.currentStep + 1,
                )
            }
        } else {
            val nextOnBoardingPageInfo = selectOnBoardingPageInfos[currentState.currentStep]
            val nextButtonEnable = nextOnBoardingPageInfo.isItemSelected

            reduce {
                currentState.copy(
                    currentOnBoardingPageInfo = nextOnBoardingPageInfo,
                    nextButtonEnable = nextButtonEnable,
                    currentStep = currentState.currentStep + 1,
                )
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

    fun selectPrevious() = intent {
        val currentState = state
        if (currentState !is OnBoardingState.Idle || currentState.currentStep == 0) {
            postSideEffect(sideEffect = OnBoardingSideEffect.MoveToPreviousScreen)
            return@intent
        }

        if (currentState.currentStep == 1) {
            if (currentState.onBoardingSetType == OnBoardingSetType.RESET && existedOnBoardingAbstract != null) {
                reduce {
                    currentState.copy(
                        currentStep = 0,
                        currentOnBoardingPageInfo = existedOnBoardingAbstract!!,
                        nextButtonEnable = true,
                    )
                }
                return@intent
            } else {
                reduce {
                    currentState.copy(
                        currentStep = 0,
                        currentOnBoardingPageInfo = OnBoardingPageInfo.Intro,
                        nextButtonEnable = true,
                    )
                }
                return@intent
            }
        }

        val isSelectOnBoardingStep = currentState.currentStep <= selectOnBoardingPageInfos.size
        if (isSelectOnBoardingStep) {
            val previousOnBoardingPageInfo = selectOnBoardingPageInfos[currentState.currentStep - 2]
            val nextButtonEnable = previousOnBoardingPageInfo.isItemSelected
            reduce {
                currentState.copy(
                    currentOnBoardingPageInfo = previousOnBoardingPageInfo,
                    nextButtonEnable = nextButtonEnable,
                    currentStep = currentState.currentStep - 1,
                )
            }
            return@intent
        } else {
            val selectOnBoardingPageInfo = selectOnBoardingPageInfos.last()
            val nextButtonEnable = selectOnBoardingPageInfo.isItemSelected

            reduce {
                currentState.copy(
                    currentOnBoardingPageInfo = selectOnBoardingPageInfo,
                    nextButtonEnable = nextButtonEnable,
                    currentStep = selectOnBoardingPageInfos.size,
                )
            }
            return@intent
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
                        applyRecommendRoutines(
                            routines = recommendRoutines.map { it.toUiModel() },
                        )
                    }
                },
                onFailure = {
                },
            )
        }
    }

    private fun applyRecommendRoutines(routines: List<OnBoardingItemUiModel>) = intent {
        val currentState = state
        if (currentState !is OnBoardingState.Idle) return@intent

        val recommendRoutinePageInfo = OnBoardingPageInfo.RecommendRoutines(routines = routines)
        reduce {
            currentState.copy(
                currentOnBoardingPageInfo = recommendRoutinePageInfo,
                currentStep = currentState.currentStep + 1,
                nextButtonEnable = !currentState.onBoardingSetType.mustSelectRecommendRoutine,
            )
        }
    }

    fun cancelLoadRecommendRoutines() {
        loadRecommendRoutinesJob?.cancel()
    }

    fun selectRoutine(routineId: String) = intent {
        val currentState = state
        if (currentState !is OnBoardingState.Idle) return@intent

        val currentPageInfo = currentState.currentOnBoardingPageInfo
        if (currentPageInfo !is OnBoardingPageInfo.RecommendRoutines) return@intent

        val selectChangedCurrentPageInfo = currentPageInfo.selectItem(itemId = routineId)
        reduce {
            currentState.copy(
                currentOnBoardingPageInfo = selectChangedCurrentPageInfo,
                nextButtonEnable = selectChangedCurrentPageInfo.isItemSelected,
            )
        }
    }

    fun registerRecommendRoutines() = intent {
        val currentState = state
        if (currentState !is OnBoardingState.Idle) return@intent

        val currentPageInfo = currentState.currentOnBoardingPageInfo
        if (currentPageInfo !is OnBoardingPageInfo.RecommendRoutines) return@intent

        val selectedRoutineIds = currentPageInfo.routines.filter { routineItem ->
            routineItem.selectedIndex != null
        }.map {
            it.id
        }

        registerRecommendOnBoardingRoutinesUseCase(selectedRecommendRoutineIds = selectedRoutineIds).fold(
            onSuccess = { _ ->
                postSideEffect(sideEffect = OnBoardingSideEffect.NavigateToHomeScreen)
            },
            onFailure = {
            },
        )
    }

    fun skipRegisterRecommendRoutines() = intent {
        postSideEffect(sideEffect = OnBoardingSideEffect.NavigateToHomeScreen)
    }
}
