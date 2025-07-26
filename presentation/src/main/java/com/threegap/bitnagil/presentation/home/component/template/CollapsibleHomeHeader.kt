package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.presentation.home.component.block.SpeechBubbleTooltip
import com.threegap.bitnagil.presentation.home.util.CollapsibleHeaderState
import com.threegap.bitnagil.presentation.home.util.rememberCollapsibleHeaderState

@Composable
fun CollapsibleHomeHeader(
    userName: String,
    collapsibleHeaderState: CollapsibleHeaderState,
    onEmotionRecordClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showTooltip by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.height(collapsibleHeaderState.currentHeaderHeight),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(collapsibleHeaderState.collapsedHeaderHeight)
                .padding(horizontal = 16.dp),
        ) {
            // TODO: 알림 아이콘 추가예정
        }

        if (collapsibleHeaderState.currentHeaderHeight > collapsibleHeaderState.collapsedHeaderHeight) {
            Box {
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(collapsibleHeaderState.currentHeaderHeight - collapsibleHeaderState.collapsedHeaderHeight)
                        .padding(horizontal = 16.dp)
                        .alpha(1f - collapsibleHeaderState.collapseProgress)
                        .align(Alignment.TopStart),
                ) {
                    GreetingMessage(
                        userName = userName,
                        showTooltip = showTooltip,
                        onInfoClick = { showTooltip = true },
                        onTooltipDismiss = { showTooltip = false },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier
                            .clickable { onEmotionRecordClick() }
                            .padding(vertical = 11.dp),
                    ) {
                        Text(
                            text = "감정구슬 기록하기",
                            style = BitnagilTheme.typography.caption1Medium,
                            color = BitnagilTheme.colors.navy300,
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = BitnagilTheme.colors.navy300,
                        )
                    }
                }

                EmotionBallIcon(
                    modifier = Modifier
                        .padding(end = 18.dp, bottom = 42.dp)
                        .align(Alignment.BottomEnd),
                )
            }
        }
    }
}

@Composable
private fun GreetingMessage(
    userName: String,
    showTooltip: Boolean,
    onInfoClick: () -> Unit,
    onTooltipDismiss: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = "${userName}님,\n오늘의 기분 어때요?",
            style = BitnagilTheme.typography.title1SemiBold,
            color = BitnagilTheme.colors.coolGray10,
        )

        Box {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onInfoClick() }
                    .padding(4.dp)
                    .size(16.dp),
            )

            if (showTooltip) {
                Popup(
                    onDismissRequest = onTooltipDismiss,
                    alignment = Alignment.TopCenter,
                    offset = IntOffset(x = 0, y = -110),
                ) {
                    SpeechBubbleTooltip(
                        text = "감정 기록 시, 루틴을 추천 받아요!",
                    )
                }
            }
        }
    }
}

@Composable
private fun EmotionBallIcon(
    modifier: Modifier = Modifier,
) {
    // TODO: 감정구슬 그래픽으로 수정하기
    Icon(
        imageVector = Icons.Default.AccountBox,
        contentDescription = "감정구슬",
        modifier = modifier.size(102.dp),
    )
}

@Preview
@Composable
private fun HomeTopBarPreview() {
    CollapsibleHomeHeader(
        userName = "대현",
        collapsibleHeaderState = rememberCollapsibleHeaderState(),
        onEmotionRecordClick = {},
    )
}
