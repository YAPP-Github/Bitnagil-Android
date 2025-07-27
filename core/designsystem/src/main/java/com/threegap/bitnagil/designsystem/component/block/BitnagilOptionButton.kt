package com.threegap.bitnagil.designsystem.component.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun BitnagilOptionButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clickableWithoutRipple(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = BitnagilTheme.colors.black,
            style = BitnagilTheme.typography.body1Regular,
            modifier = Modifier.weight(1f)
        )

        BitnagilIcon(
            id = R.drawable.ic_right_arrow_20,
            tint = BitnagilTheme.colors.black,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun BitnagilOptionButtonPreview() {
    BitnagilOptionButton(
        title = "공지사항",
        onClick = {}
    )
}
