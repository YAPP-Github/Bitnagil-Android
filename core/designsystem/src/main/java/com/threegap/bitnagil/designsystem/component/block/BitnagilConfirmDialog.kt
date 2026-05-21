package com.threegap.bitnagil.designsystem.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BitnagilConfirmDialog(
    title: String,
    description: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    BasicAlertDialog(
        onDismissRequest = onConfirm,
        modifier = modifier,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
        ),
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.white,
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(vertical = 20.dp, horizontal = 24.dp),
        ) {
            Text(
                text = title,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.subtitle1SemiBold,
                modifier = Modifier.padding(bottom = 6.dp),
            )

            Text(
                text = description,
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
                modifier = Modifier.padding(bottom = 18.dp),
            )

            Text(
                text = confirmButtonText,
                color = BitnagilTheme.colors.orange500,
                style = BitnagilTheme.typography.body2Medium,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRipple { onConfirm() },
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BitnagilConfirmDialog(
        title = "루틴 완료를 저장하지 못했어요",
        description = "네트워크 연결에 문제가 있었던 것 같아요.\n다시 한 번 시도해 주세요.",
        confirmButtonText = "확인",
        onConfirm = {},
    )
}
