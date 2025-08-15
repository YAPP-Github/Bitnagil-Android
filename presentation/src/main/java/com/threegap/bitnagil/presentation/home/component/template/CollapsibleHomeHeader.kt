package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.presentation.home.component.atom.EmotionRegisterButton
import com.threegap.bitnagil.presentation.home.model.TodayEmotionUiModel
import com.threegap.bitnagil.presentation.home.util.CollapsibleHeaderState
import com.threegap.bitnagil.presentation.home.util.rememberCollapsibleHeaderState

@Composable
fun CollapsibleHomeHeader(
    userName: String,
    todayEmotion: TodayEmotionUiModel?,
    collapsibleHeaderState: CollapsibleHeaderState,
    onRegisterEmotion: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val alpha by animateFloatAsState(
        targetValue = 1f - collapsibleHeaderState.collapseProgress,
        animationSpec = tween(durationMillis = 300),
        label = "header_alpha",
    )
    val hasEmotion = todayEmotion != null
    val welcomeMessage = if (hasEmotion) {
        "${userName}님,\n${todayEmotion?.homeMessage}"
    } else {
        "${userName}님, 오셨군요!\n오늘 기분은 어떤가요?"
    }

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
                id = R.drawable.ic_logo,
                tint = BitnagilTheme.colors.coolGray50,
                modifier = Modifier.padding(start = 16.dp),
            )
        }

        if (collapsibleHeaderState.currentHeaderHeight > collapsibleHeaderState.collapsedHeaderHeight) {
            Box(
                modifier = Modifier
                    .padding(top = 18.dp)
                    .height(collapsibleHeaderState.currentHeaderHeight - collapsibleHeaderState.collapsedHeaderHeight)
                    .alpha(alpha),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopStart),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Text(
                        text = welcomeMessage,
                        style = BitnagilTheme.typography.cafe24SsurroundAir,
                        color = BitnagilTheme.colors.white,
                        fontWeight = FontWeight.SemiBold,
                    )

                    EmotionRegisterButton(
                        onClick = onRegisterEmotion,
                        enabled = !hasEmotion,
                    )
                }

                AsyncImage(
                    model = remember(todayEmotion?.imageUrl) {
                        ImageRequest.Builder(context)
                            .data(todayEmotion?.imageUrl)
                            .crossfade(true)
                            .build()
                    },
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.default_emotion),
                    error = painterResource(R.drawable.default_emotion),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 18.dp)
                        .aspectRatio(108f / 148f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun CollapsibleHomeHeaderPreview() {
    CollapsibleHomeHeader(
        userName = "대현",
        todayEmotion = null,
        collapsibleHeaderState = rememberCollapsibleHeaderState(),
        onRegisterEmotion = {},
    )
}
