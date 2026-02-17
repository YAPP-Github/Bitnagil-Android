package com.threegap.bitnagil.presentation.mypage.contract

data class MyPageState(
    val name: String,
    val profileUrl: String,
) {
    companion object {
        val INIT = MyPageState(
            name = "",
            profileUrl = "",
        )
    }
}
