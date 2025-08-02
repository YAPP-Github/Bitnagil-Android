package com.threegap.bitnagil.presentation.mypage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.user.usecase.FetchUserProfileUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.mypage.model.MyPageIntent
import com.threegap.bitnagil.presentation.mypage.model.MyPageSideEffect
import com.threegap.bitnagil.presentation.mypage.model.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
) : MviViewModel<MyPageState, MyPageSideEffect, MyPageIntent>(
    MyPageState.Init,
    savedStateHandle,
) {
    init {
        loadMyPageInfo()
    }

    private fun loadMyPageInfo() {
        viewModelScope.launch {
            fetchUserProfileUseCase().fold(
                onSuccess = {
                    sendIntent(
                        MyPageIntent.LoadMyPageSuccess(
                            name = it.nickname,
                            profileUrl = "profileUrl",
                        ),
                    )
                },
                onFailure = {
                },
            )
        }
    }

    override suspend fun SimpleSyntax<MyPageState, MyPageSideEffect>.reduceState(
        intent: MyPageIntent,
        state: MyPageState,
    ): MyPageState {
        when (intent) {
            is MyPageIntent.LoadMyPageSuccess -> {
                return state.copy(
                    name = intent.name,
                    profileUrl = intent.profileUrl,
                )
            }
        }
    }
}
