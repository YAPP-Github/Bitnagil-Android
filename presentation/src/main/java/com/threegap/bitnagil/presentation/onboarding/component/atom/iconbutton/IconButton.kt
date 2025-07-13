package com.threegap.bitnagil.presentation.onboarding.component.atom.iconbutton

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.svg.SvgDecoder

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    @DrawableRes svgResourceId: Int,
    contentDescription: String? = null,
    onClick: () -> Unit,
    defaultColor: Color = Color.Black,
    pressedColor: Color = Color.Gray,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val context = LocalContext.current

    val iconColor = if (isPressed) pressedColor else defaultColor

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    Box(
        modifier = modifier
            .size(36.dp) // 전체 버튼 크기
            .clickable(
                interactionSource = interactionSource,
                indication = null, // 기본 Ripple 효과 제거 (필요시 null 대신 rememberRipple() 사용)
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center, // 내부 아이콘을 중앙에 배치
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = svgResourceId, // 앱 리소스 ID 직접 사용
                imageLoader = imageLoader,
            ),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp), // 내부 아이콘 크기
            colorFilter = ColorFilter.tint(iconColor), // 아이콘 색상 적용
        )
    }
}
