package com.threegap.bitnagil.presentation.guide.contract

import com.threegap.bitnagil.presentation.guide.model.GuideType

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
