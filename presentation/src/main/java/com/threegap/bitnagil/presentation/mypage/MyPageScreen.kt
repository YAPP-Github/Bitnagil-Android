package com.threegap.bitnagil.presentation.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
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
        onClickResetOnBoarding = navigateToQnA,
        onClickQnA = navigateToOnBoarding,
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
            .background(color = BitnagilTheme.colors.white),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth(),
        ) {
            Text(
                "마이페이지",
                modifier = Modifier.align(Alignment.Center),
                style = BitnagilTheme.typography.title3SemiBold,
            )

            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(36.dp)
                    .background(BitnagilTheme.colors.black)
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = onClickSetting),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = ColorPainter(BitnagilTheme.colors.coolGray98),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(shape = CircleShape),
        )

        Text(
            state.name,
            style = BitnagilTheme.typography.title3SemiBold,
            modifier = Modifier.padding(top = 12.dp),
        )

        Spacer(modifier = Modifier.height(28.dp))

        HorizontalDivider(modifier = Modifier.height(6.dp), thickness = 6.dp, color = BitnagilTheme.colors.coolGray98)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .height(48.dp)
                .clickable(onClick = onClickResetOnBoarding)
                .padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "내 목표 재설정",
                style = BitnagilTheme.typography.body1Regular,
                modifier = Modifier.weight(1f),
            )

            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(36.dp)
                    .background(BitnagilTheme.colors.black),
            )
        }

        Row(
            modifier = Modifier
                .height(48.dp)
                .clickable(onClick = onClickNotice)
                .padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "공지사항",
                style = BitnagilTheme.typography.body1Regular,
                modifier = Modifier.weight(1f),
            )

            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(36.dp)
                    .background(BitnagilTheme.colors.black),
            )
        }

        Row(
            modifier = Modifier
                .height(48.dp)
                .clickable(onClick = onClickQnA)
                .padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "자주 묻는 질문",
                style = BitnagilTheme.typography.body1Regular,
                modifier = Modifier.weight(1f),
            )

            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(36.dp)
                    .background(BitnagilTheme.colors.black),
            )
        }
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
