package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.threegap.bitnagil.designsystem.BitnagilTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmDialog(
    onDeleteToday: () -> Unit,
    onDeleteAll: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.white,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(24.dp),
        ) {
            Text(
                text = "해당 루틴은\n반복 루틴으로 설정되어 있어요",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDeleteToday() }
                        .height(44.dp)
                        .background(
                            color = BitnagilTheme.colors.navy500,
                            shape = RoundedCornerShape(8.dp),
                        ),
                ) {
                    Text(
                        text = "선택한 요일만 삭제",
                        style = BitnagilTheme.typography.body2Medium,
                        color = BitnagilTheme.colors.white,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDeleteAll() }
                        .height(44.dp)
                        .background(
                            color = BitnagilTheme.colors.white,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .border(
                            width = 1.dp,
                            color = BitnagilTheme.colors.navy500,
                            shape = RoundedCornerShape(8.dp),
                        ),
                ) {
                    Text(
                        text = "전체 루틴 삭제",
                        style = BitnagilTheme.typography.body2Medium,
                        color = BitnagilTheme.colors.black,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DeleteConfirmDialogPreview() {
    BitnagilTheme {
        DeleteConfirmDialog(
            onDeleteToday = {},
            onDeleteAll = {},
            onDismiss = {},
        )
    }
}
