package com.threegap.bitnagil.presentation.onboarding.model.mvi

import android.os.Parcelable
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OnBoardingState(val progress: Float) : Parcelable, MviState {

    @Parcelize
    object Loading : OnBoardingState(progress = 0f)

    @Parcelize
    data class Idle(
        val nextButtonEnable: Boolean,
        val currentOnBoardingPageInfo: OnBoardingPageInfo,
        val totalStep: Int,
        val currentStep: Int,
    ) : OnBoardingState(progress = currentStep.toFloat() / totalStep.toFloat())
}
