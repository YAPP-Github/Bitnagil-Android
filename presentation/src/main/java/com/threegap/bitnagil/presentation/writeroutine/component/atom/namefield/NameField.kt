package com.threegap.bitnagil.presentation.writeroutine.component.atom.namefield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

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
            .background(BitnagilTheme.colors.coolGray99, RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .height(52.dp),
        singleLine = true,
        textStyle = BitnagilTheme.typography.body2SemiBold,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(start = 24.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            placeholder,
                            style = BitnagilTheme.typography.body2SemiBold.copy(color = BitnagilTheme.colors.coolGray90),
                        )
                    }
                    innerTextField()
                }

                if (onClickRemove != null) {
                    IconButton(
                        onClick = onClickRemove,
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "텍스트 전체 삭제",
                            tint = Color.Gray,
                        )
                    }
                }
            }
        },
    )
}
