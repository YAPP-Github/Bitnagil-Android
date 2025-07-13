package com.threegap.bitnagil.presentation.onboarding.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo

sealed class OnBoardingIntent : MviIntent {
    data class LoadOnBoardingSuccess(val onBoardingPageInfos: List<OnBoardingPageInfo.SelectOnBoarding>) : OnBoardingIntent()
    data class LoadOnBoardingAbstractSuccess(val onBoardingAbstract: OnBoardingPageInfo.Abstract) : OnBoardingIntent()
    data class LoadRecommendRoutinesSuccess(val routineList: List<OnBoardingItem>) : OnBoardingIntent()
    data class SelectItem(val itemId: String) : OnBoardingIntent()
    data class SelectRoutine(val routineId: String) : OnBoardingIntent()
    data object SelectNext : OnBoardingIntent()
    data object SelectPrevious : OnBoardingIntent()
    data object NavigateToHome : OnBoardingIntent()
}
