package com.threegap.bitnagil.presentation.screen.guide.contract

import com.threegap.bitnagil.presentation.screen.guide.model.GuideType

data class GuideState(
    val guideType: GuideType?,
    val guideBottomSheetVisible: Boolean,
) {
    companion object {
        val INIT = GuideState(
            guideType = null,
            guideBottomSheetVisible = false,
        )
    }
}
