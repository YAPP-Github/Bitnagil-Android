package com.threegap.bitnagil.presentation.guide.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class GuideIntent : MviIntent {
    data object OnHideGuideBottomSheet : GuideIntent()
    data object OnBackClick : GuideIntent()
    data class OnClickGuideButton(val guideType: GuideType) : GuideIntent()
}
