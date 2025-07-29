package com.threegap.bitnagil.domain.recommendroutine.model

enum class RecommendCategory(
    val categoryName: String,
    val displayName: String,
    val isVisible: Boolean = true,
) {
    PERSONALIZED("PERSONALIZED", "맞춤 추천"),
    OUTING("OUTING", "나가봐요"),
    WAKE_UP("WAKE_UP", "일어나요"),
    CONNECT("CONNECT", "연결해요"),
    REST("REST", "쉬어가요"),
    GROW("GROW", "성장해요"),
    OUTING_REPORT("OUTING_REPORT", "신고", isVisible = false),
    UNKNOWN("UNKNOWN", "알 수 없음", isVisible = false),
    ;

    companion object {
        fun fromString(categoryName: String): RecommendCategory =
            entries.find { it.categoryName == categoryName } ?: UNKNOWN
    }
}
