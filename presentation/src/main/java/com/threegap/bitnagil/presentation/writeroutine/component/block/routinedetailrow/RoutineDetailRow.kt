package com.threegap.bitnagil.presentation.writeroutine.component.block.routinedetailrow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.presentation.writeroutine.component.atom.writeroutinebutton.WriteRoutineButton

@Composable
fun RoutineDetailRow(
    title: String,
    placeHolder: String,
    value: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, style = BitnagilTheme.typography.body2SemiBold.copy(color = BitnagilTheme.colors.coolGray30))

        WriteRoutineButton.Custom(
            modifier = Modifier.width(120.dp),
            onClick = onClick,
            isSelected = false,
        ) {
            Box(
                modifier = Modifier.padding(vertical = 10.dp),
            ) {
                if (value.isEmpty()) {
                    Text(placeHolder, style = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.coolGray90))
                }

                Text(value, style = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.coolGray30))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 300, heightDp = 300)
fun RoutineDetailRowPreview() {
    BitnagilTheme {
        RoutineDetailRow(
            title = "시작일",
            placeHolder = "눌러서 선택",
            value = "",
            onClick = {},
        )
    }
}
