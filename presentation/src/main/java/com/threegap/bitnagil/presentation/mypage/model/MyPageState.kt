package com.threegap.bitnagil.presentation.mypage.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyPageState(
    val name: String,
    val profileUrl: String,
) : MviState {
    companion object {
        val Init = MyPageState(name = "", profileUrl = "")
    }
}
