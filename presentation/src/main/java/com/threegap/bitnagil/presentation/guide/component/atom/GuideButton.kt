package com.threegap.bitnagil.presentation.guide.component.atom

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
fun GuideButton(
    title: String,
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
            text = title,
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.body2Medium,
        )

        BitnagilIcon(
            id = R.drawable.ic_chevron_right_md,
            tint = BitnagilTheme.colors.coolGray10,
        )
    }
}

@Preview
@Composable
private fun GuideButtonPreview() {
    GuideButton(
        title = "루틴 수정하기란?",
        onClick = {},
    )
}
