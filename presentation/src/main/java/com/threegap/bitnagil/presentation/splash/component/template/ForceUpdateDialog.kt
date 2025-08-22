package com.threegap.bitnagil.presentation.splash.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForceUpdateDialog(
    onUpdateClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
        modifier = modifier
            .background(
                color = BitnagilTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(vertical = 20.dp, horizontal = 24.dp),
    ) {
        Column(
            modifier = Modifier,
        ) {
            Text(
                text = "최신 버전으로 업데이트가 필요해요",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.subtitle1SemiBold.copy(
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None,
                    ),
                ),
            )

            Text(
                text = "더 안정적이고 안전한 서비스를 위해\n최신 버전으로 업데이트해 주세요.",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium.copy(
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None,
                    ),
                ),
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                BitnagilTextButton(
                    text = "나가기",
                    onClick = onDismissRequest,
                    colors = BitnagilTextButtonColor.cancel(),
                    textStyle = BitnagilTheme.typography.body2Medium,
                    modifier = Modifier.weight(1f),
                )

                BitnagilTextButton(
                    text = "업데이트",
                    onClick = onUpdateClick,
                    textStyle = BitnagilTheme.typography.body2Medium,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ForceUpdateDialogPreview() {
    ForceUpdateDialog(
        onUpdateClick = {},
        onDismissRequest = {},
    )
}
