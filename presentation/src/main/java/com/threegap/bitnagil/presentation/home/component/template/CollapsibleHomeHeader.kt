package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.presentation.home.component.atom.EmotionBall
import com.threegap.bitnagil.presentation.home.component.atom.EmotionRegisterButton
import com.threegap.bitnagil.presentation.home.model.EmotionBallType
import com.threegap.bitnagil.presentation.home.util.CollapsibleHeaderState
import com.threegap.bitnagil.presentation.home.util.rememberCollapsibleHeaderState

@Composable
fun CollapsibleHomeHeader(
    userName: String,
    emotionBallType: EmotionBallType?,
    collapsibleHeaderState: CollapsibleHeaderState,
    onRegisterEmotion: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(collapsibleHeaderState.currentHeaderHeight),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(collapsibleHeaderState.collapsedHeaderHeight)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BitnagilIcon(
                id = com.threegap.bitnagil.designsystem.R.drawable.ic_logo,
                tint = BitnagilTheme.colors.coolGray50,
                modifier = Modifier.padding(start = 16.dp),
            )
        }

        if (collapsibleHeaderState.currentHeaderHeight > collapsibleHeaderState.collapsedHeaderHeight) {
            Box(
                modifier = Modifier
                    .padding(top = 18.dp)
                    .height(collapsibleHeaderState.currentHeaderHeight - collapsibleHeaderState.collapsedHeaderHeight)
                    .alpha(1f - collapsibleHeaderState.collapseProgress),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopStart),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Text(
                        text = "${userName}님, 오셨군요!\n오늘 기분은 어떤가요?",
                        style = BitnagilTheme.typography.cafe24SsurroundAir,
                        color = BitnagilTheme.colors.white,
                    )

                    EmotionRegisterButton(
                        onClick = onRegisterEmotion,
                        enabled = emotionBallType == null,
                    )
                }

                Box(
                    modifier = modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 18.dp),
                ) {
                    EmotionBall(
                        emotionType = emotionBallType,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CollapsibleHomeHeaderPreview() {
    CollapsibleHomeHeader(
        userName = "대현",
        emotionBallType = null,
        collapsibleHeaderState = rememberCollapsibleHeaderState(),
        onRegisterEmotion = {},
    )
}
