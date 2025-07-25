package com.threegap.bitnagil.presentation.mypage.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class MyPageIntent : MviIntent {
    data class LoadMyPageSuccess(val name: String, val profileUrl: String) : MyPageIntent()
}
