package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.presentation.common.ninepatch.ninePatchBackgroundNode

@Composable
fun OnBoardingIntroTemplate(
    userName: String,
    onClickNextButton: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "포모는 ${userName}님을 알고 싶어요!",
            style = BitnagilTheme.typography.title1Bold,
            color = BitnagilTheme.colors.coolGray10,
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .ninePatchBackgroundNode(R.drawable.img_texture_rectangle_1)
                    .padding(horizontal = 27.dp, vertical = 20.dp),
                text = "어떤 시간대를 더 잘보내고 싶은지",
                style = BitnagilTheme.typography.button1,
                color = BitnagilTheme.colors.coolGray10,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .offset(y = (-10).dp)
                    .ninePatchBackgroundNode(R.drawable.img_texture_rectangle_2)
                    .padding(horizontal = 27.dp, vertical = 20.dp),
                text = "요즘 어떤 회복이 필요한지",
                style = BitnagilTheme.typography.button1,
                color = BitnagilTheme.colors.coolGray10,
            )
            Text(
                modifier = Modifier
                    .offset(y = (-18).dp)
                    .padding(start = 22.dp)
                    .ninePatchBackgroundNode(R.drawable.img_texture_rectangle_3)
                    .padding(horizontal = 27.dp, vertical = 20.dp),
                text = "얼마나 바깥 바람을 쐬고 싶은지",
                style = BitnagilTheme.typography.button1,
                color = BitnagilTheme.colors.coolGray10,
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(R.drawable.img_pomo_memo),
            contentDescription = null,
            modifier = Modifier
                .widthIn(min = 263.dp)
                .align(Alignment.End),
        )

        Spacer(modifier = Modifier.height(40.dp))

        BitnagilTextButton(
            text = "포모에게 알려주기",
            onClick = onClickNextButton,
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@Preview(showBackground = true, heightDp = 800, widthDp = 360)
private fun OnBoardingIntroTemplatePreview() {
    BitnagilTheme {
        OnBoardingIntroTemplate(
            userName = "안드로이드",
            onClickNextButton = {},
        )
    }
}
