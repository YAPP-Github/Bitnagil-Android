package com.threegap.bitnagil.presentation.common.extension

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel

val RecommendLevel.displayLevel: String
    get() = when (this) {
        RecommendLevel.LEVEL1 -> "하"
        RecommendLevel.LEVEL2 -> "중"
        RecommendLevel.LEVEL3 -> "상"
    }

val RecommendLevel.displayTitle: String
    get() = when (this) {
        RecommendLevel.LEVEL1 -> "가볍게 할 수 있어요"
        RecommendLevel.LEVEL2 -> "조금 신경써서 할 수 있어요"
        RecommendLevel.LEVEL3 -> "의지를 다 잡고 할 수 있어요"
    }
