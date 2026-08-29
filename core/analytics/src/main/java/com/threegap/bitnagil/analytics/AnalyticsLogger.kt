package com.threegap.bitnagil.analytics

interface AnalyticsLogger {
    /**
     * 회원가입 완료를 기록한다. 약관 동의가 서버에 확정된 시점에 호출된다.
     */
    fun logSignUpCompleted()

    /**
     * 온보딩 완료를 기록한다. 추천 루틴을 등록했든 건너뛰었든 동일하게 기록한다.
     */
    fun logOnBoardingCompleted()

    /**
     * 생애 최초 루틴 완료를 기록한다. 앱 설치 기준으로 1회만 호출된다.
     */
    fun logFirstRoutineCompleted()
}
