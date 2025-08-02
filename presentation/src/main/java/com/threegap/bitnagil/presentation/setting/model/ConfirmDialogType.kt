package com.threegap.bitnagil.presentation.setting.model

enum class ConfirmDialogType(
    val titleText: String,
    val descriptionText: String,
    val confirmButtonText: String,
) {
    LOGOUT(
        titleText = "로그아웃 하시겠어요?",
        descriptionText = "버튼을 누르면 로그인 페이지로 이동해요.",
        confirmButtonText = "로그아웃",
    ),
    WITHDRAW(
        titleText = "정말 탈퇴하시겠어요?",
        descriptionText = "소중한 기록들이 모두 사라져요.",
        confirmButtonText = "탈퇴하기",
    ),
}
