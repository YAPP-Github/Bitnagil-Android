package com.threegap.bitnagil.presentation.writeroutine.component.atom.namefield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun NameField(
    value: String,
    onValueChange: (String) -> Unit,
    onClickRemove: (() -> Unit)?,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        textStyle = BitnagilTheme.typography.body2SemiBold,
        decorationBox = { innerTextField ->
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                placeholder,
                                style = BitnagilTheme.typography.title3SemiBold.copy(color = BitnagilTheme.colors.coolGray90),
                            )
                        }
                        innerTextField()
                    }

                    if (onClickRemove != null) {
                        Box(
                            modifier = Modifier.clickableWithoutRipple(onClick = onClickRemove),
                            contentAlignment = Alignment.Center
                        ) {
                            BitnagilIcon(
                                id = R.drawable.ic_close_circle,
                                tint = null,
                                modifier = Modifier.size(24.dp).padding(top = 4.dp, bottom = 4.dp, start = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.height(2.dp),
                    color = BitnagilTheme.colors.coolGray90,
                )
            }
        },
    )
}

@Composable
@Preview(showBackground = true, widthDp = 300, heightDp = 300)
fun NameFieldPreview() {
    BitnagilTheme{
        NameField(
            value = "value",
            onValueChange = {},
            onClickRemove = {},
        )
    }
}
