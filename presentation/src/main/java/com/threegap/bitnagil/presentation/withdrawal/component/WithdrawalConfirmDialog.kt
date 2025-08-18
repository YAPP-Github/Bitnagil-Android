package com.threegap.bitnagil.presentation.withdrawal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun WithdrawalConfirmDialog(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = {},
        modifier = modifier,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
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
                text = "탈퇴가 완료되었어요",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.subtitle1SemiBold,
                modifier = Modifier.padding(bottom = 6.dp),
            )

            Text(
                text = "이용해 주셔서 감사합니다. 언제든 다시\n돌아오실 수 있어요:)",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
                modifier = Modifier.padding(bottom = 18.dp),
            )

            Text(
                text = "확인",
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
private fun WithdrawalConfirmDialogPreview() {
    WithdrawalConfirmDialog(
        onConfirm = {},
    )
}
