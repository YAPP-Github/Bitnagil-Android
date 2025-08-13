package com.threegap.bitnagil.presentation.writeroutine.component.atom.selectcell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun SelectCell(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(38.dp)
            .background(
                color = if (selected) BitnagilTheme.colors.orange500 else BitnagilTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(12.dp),
                color = if (selected) BitnagilTheme.colors.orange500 else BitnagilTheme.colors.coolGray96,
            )
            .clickableWithoutRipple(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = if (selected) BitnagilTheme.colors.white else BitnagilTheme.colors.coolGray30,
            style = BitnagilTheme.typography.body2Medium,
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 300, heightDp = 300)
private fun SelectCellPreview() {
    BitnagilTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SelectCell(
                text = "월",
                selected = true,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            SelectCell(
                text = "화",
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            SelectCell(
                text = "수",
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            SelectCell(
                text = "목",
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            SelectCell(
                text = "금",
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            SelectCell(
                text = "토",
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            SelectCell(
                text = "일",
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
        }
    }
}
