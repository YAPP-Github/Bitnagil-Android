package com.threegap.bitnagil.presentation.writeroutine.component.block.subroutinefield

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun SubRoutineField(
    resourceId: Int,
    placeHolder: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(resourceId),
            contentDescription = null,
            modifier = Modifier.size(21.dp),
        )

        Spacer(modifier = Modifier.width(12.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            enabled = enabled,
            textStyle = BitnagilTheme.typography.body2Medium.copy(color = if (enabled) BitnagilTheme.colors.coolGray30 else BitnagilTheme.colors.coolGray90),
            decorationBox = { innerTextField ->
                Column {
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = placeHolder,
                                style = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.coolGray90),
                            )
                        }
                        innerTextField()
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.height(1.dp),
                        color = BitnagilTheme.colors.coolGray90,
                    )
                }
            },
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 300, heightDp = 300)
fun NameFieldPreview() {
    BitnagilTheme {
        SubRoutineField(
            resourceId = com.threegap.bitnagil.designsystem.R.drawable.img_circle_1,
            placeHolder = "세부루틴을 설정해주세요.",
            value = "TEXT",
            onValueChange = {},
            enabled = true,
        )
    }
}
