package com.threegap.bitnagil.presentation.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton
import com.threegap.bitnagil.designsystem.component.block.BitnagilOptionButton
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.mypage.model.MyPageState

@Composable
fun MyPageScreenContainer(
    myPageViewModel: MyPageViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
    navigateToNotice: () -> Unit,
    navigateToQnA: () -> Unit,
    navigateToOnBoarding: () -> Unit,
) {
    val state by myPageViewModel.stateFlow.collectAsState()

    MyPageScreen(
        state = state,
        onClickSetting = navigateToSetting,
        onClickNotice = navigateToNotice,
        onClickResetOnBoarding = navigateToOnBoarding,
        onClickQnA = navigateToQnA,
    )
}

@Composable
private fun MyPageScreen(
    state: MyPageState,
    onClickSetting: () -> Unit,
    onClickNotice: () -> Unit,
    onClickResetOnBoarding: () -> Unit,
    onClickQnA: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.white)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BitnagilTopBar(
            title = "마이페이지",
            actions = {
                BitnagilIconButton(
                    id = R.drawable.ic_setting,
                    onClick = onClickSetting,
                    tint = null,
                    paddingValues = PaddingValues(6.dp),
                )
            },
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.img_default_progile),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(shape = CircleShape),
        )

        Text(
            text = state.name,
            color = BitnagilTheme.colors.coolGray5,
            style = BitnagilTheme.typography.title3SemiBold,
            modifier = Modifier.padding(top = 12.dp),
        )

        Spacer(modifier = Modifier.height(28.dp))

        HorizontalDivider(modifier = Modifier.height(6.dp), thickness = 6.dp, color = BitnagilTheme.colors.coolGray98)

        Spacer(modifier = Modifier.height(16.dp))

        BitnagilOptionButton(
            title = "내 목표 재설정",
            onClick = onClickResetOnBoarding,
        )

        BitnagilOptionButton(
            title = "공지사항",
            onClick = onClickNotice,
        )

        BitnagilOptionButton(
            title = "자주 묻는 질문",
            onClick = onClickQnA,
        )
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    BitnagilTheme {
        MyPageScreen(
            state = MyPageState(name = "이름", profileUrl = ""),
            onClickSetting = { },
            onClickNotice = { },
            onClickResetOnBoarding = { },
            onClickQnA = { },
        )
    }
}
