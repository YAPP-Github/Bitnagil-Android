package com.threegap.bitnagil.designsystem.component.atom

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes id: Int? = null,
) {
    Box(
        modifier = modifier
            .background(
                color = BitnagilTheme.colors.navy400,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(vertical = 10.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (id != null) {
                BitnagilIcon(
                    id = id,
                    tint = BitnagilTheme.colors.error,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp),
                )
            }
            Text(
                text = text,
                color = BitnagilTheme.colors.white,
                style = BitnagilTheme.typography.body2Medium,
                textAlign = TextAlign.Center,
            )
        }
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
    private var _icon by mutableStateOf<Int?>(null)
    private var _isVisible by mutableStateOf(false)
    private var _toastId by mutableIntStateOf(0)
    private var _lastShowTime = 0L

    val text: String get() = _text
    val icon: Int? get() = _icon
    val isVisible: Boolean get() = _isVisible
    val toastId: Int get() = _toastId

    fun show(text: String, icon: Int? = null) {
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

    private fun showToast(text: String, icon: Int?) {
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
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        BitnagilToastMessage(
            text = "버튼을 한 번 더 누르면 종료됩니다.",
            id = R.drawable.ic_warning,
        )

        BitnagilToastMessage(
            text = "루틴 완료 상태 저장에 실패했어요.\n다시 시도해 주세요.",
        )
    }
}
