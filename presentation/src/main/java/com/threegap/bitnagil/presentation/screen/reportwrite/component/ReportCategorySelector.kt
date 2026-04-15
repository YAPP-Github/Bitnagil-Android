package com.threegap.bitnagil.presentation.screen.reportwrite.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ReportCategorySelector(
    title: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = BitnagilTheme.colors.coolGray99,
                shape = RoundedCornerShape(12.dp),
            )
            .fillMaxWidth()
            .height(56.dp)
            .clickableWithoutRipple { onClick() }
            .padding(vertical = 14.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title ?: "카테고리 선택",
            color = if (title == null) BitnagilTheme.colors.coolGray80 else BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.body2Medium,
        )

        BitnagilIcon(
            id = R.drawable.icon_down_arrow_black,
            tint = BitnagilTheme.colors.coolGray10,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ReportCategorySelector(
        title = "카테고리 선택",
        onClick = {},
    )
}
