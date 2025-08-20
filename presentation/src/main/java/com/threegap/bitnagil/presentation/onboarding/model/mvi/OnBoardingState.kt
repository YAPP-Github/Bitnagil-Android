package com.threegap.bitnagil.presentation.onboarding.model.mvi

import android.os.Parcelable
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingPageInfo
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingSetType
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
        val onBoardingSetType: OnBoardingSetType,
        val userName: String,
    ) : OnBoardingState(progress = currentStep.toFloat() / totalStep.toFloat())

    val showProgress: Boolean get() = progress > 0
    val hideToolbar: Boolean get() = (this is Idle && onBoardingSetType == OnBoardingSetType.NEW && progress <= 0)
}
