package com.threegap.bitnagil.presentation.withdrawal.model

enum class WithdrawalReason(
    val displayText: String,
) {
    ROUTINE_MISMATCH("루틴이 생활 패턴과 맞지 않아요."),
    COMPLEX_UI("기능이 복잡하거나 사용이 불편해요."),
    TECHNICAL_ISSUES("앱이 자주 멈추거나 오류가 발생해요."),
}
