package com.threegap.bitnagil.analytics

interface AnalyticsLogger {
    /**
     * 온보딩 완료를 기록한다. 추천 루틴을 등록했든 건너뛰었든 동일하게 기록한다.
     */
    fun logOnBoardingCompleted()
}
