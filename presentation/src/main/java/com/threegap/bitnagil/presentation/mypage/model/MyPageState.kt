package com.threegap.bitnagil.presentation.mypage.model

data class MyPageState(
    val name: String,
    val profileUrl: String,
) {
    companion object {
        val Init = MyPageState(name = "", profileUrl = "")
    }
}
