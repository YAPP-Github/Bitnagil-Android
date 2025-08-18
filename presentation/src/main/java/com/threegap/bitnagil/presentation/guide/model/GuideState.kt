package com.threegap.bitnagil.presentation.guide.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class GuideState(
    val guideType: GuideType? = null,
    val guideBottomSheetVisible: Boolean = false,
) : MviState
