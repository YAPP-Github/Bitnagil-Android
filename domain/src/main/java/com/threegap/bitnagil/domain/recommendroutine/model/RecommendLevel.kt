package com.threegap.bitnagil.domain.recommendroutine.model

enum class RecommendLevel(
    val level: String,
    val displayName: String,
) {
    LEVEL1("LEVEL1", "가볍게 할 수 있어요"),
    LEVEL2("LEVEL2", "조금 신경써서 할 수 있어요"),
    LEVEL3("LEVEL3", "의지를 다 잡고 할 수 있어요"),
    ;

    companion object {
        fun fromString(levelName: String): RecommendLevel =
            entries.find { it.level == levelName } ?: LEVEL1
    }
}
