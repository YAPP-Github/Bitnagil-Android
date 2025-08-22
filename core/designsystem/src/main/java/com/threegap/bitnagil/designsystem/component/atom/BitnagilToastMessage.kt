package com.threegap.bitnagil.designsystem.component.atom

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import kotlinx.coroutines.delay

@Composable
fun BitnagilToastMessage(
    @DrawableRes id: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(
                color = BitnagilTheme.colors.coolGray30,
                shape = RoundedCornerShape(12.dp),
            )
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        BitnagilIcon(
            id = id,
            tint = null,
            modifier = Modifier.size(24.dp),
        )

        Text(
            text = text,
            color = BitnagilTheme.colors.white,
            style = BitnagilTheme.typography.body2Medium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun BitnagilToastContainer(
    state: BitnagilToastState,
    modifier: Modifier = Modifier,
    duration: Long = 2000L,
) {
    if (state.isVisible) {
        LaunchedEffect(state.toastId) {
            if (state.isVisible) {
                delay(duration)
                state.hide()
            }
        }

        AnimatedVisibility(
            visible = state.isVisible,
            enter = fadeIn() + slideInVertically { it / 2 },
            exit = fadeOut() + slideOutVertically { it / 2 },
            modifier = modifier,
        ) {
            BitnagilToastMessage(
                text = state.text,
                id = state.icon,
            )
        }
    }
}

class BitnagilToastState {
    private var _text by mutableStateOf("")
    private var _icon by mutableIntStateOf(0)
    private var _isVisible by mutableStateOf(false)
    private var _toastId by mutableIntStateOf(0)
    private var _lastShowTime = 0L

    val text: String get() = _text
    val icon: Int get() = _icon
    val isVisible: Boolean get() = _isVisible
    val toastId: Int get() = _toastId

    fun show(text: String, icon: Int) {
        if (shouldPreventDuplicateShow(text, icon)) {
            return
        }
        showToast(text, icon)
    }

    fun hide() {
        _isVisible = false
    }

    private fun shouldPreventDuplicateShow(text: String, icon: Int?): Boolean {
        val currentTime = System.currentTimeMillis()
        return _text == text && _icon == icon && currentTime - _lastShowTime < 500L
    }

    private fun showToast(text: String, icon: Int) {
        _text = text
        _icon = icon
        _isVisible = true
        _lastShowTime = System.currentTimeMillis()
        _toastId += 1
    }
}

@Composable
fun rememberBitnagilToast(): BitnagilToastState = remember { BitnagilToastState() }

@Preview
@Composable
private fun BitnagilToastMessagePreview() {
    BitnagilToastMessage(
        text = "버튼을 한 번 더 누르면 종료됩니다.",
        id = R.drawable.ic_warning,
    )
}
