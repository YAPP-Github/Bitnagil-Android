package com.threegap.bitnagil.presentation.screen.reportwrite.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun CurrentLocationInput(
    currentLocation: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(52.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = currentLocation ?: "현재 위치 검색",
            color = if (currentLocation == null) BitnagilTheme.colors.coolGray80 else BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.body2Medium,
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.coolGray99,
                    shape = RoundedCornerShape(14.dp),
                )
                .fillMaxHeight()
                .padding(vertical = 16.dp, horizontal = 20.dp)
                .weight(1f),
        )

        Box(
            modifier = modifier
                .background(
                    color = BitnagilTheme.colors.orange500,
                    shape = RoundedCornerShape(12.dp),
                )
                .fillMaxHeight()
                .padding(14.dp)
                .clickableWithoutRipple(
                    onClick = { onClick() },
                ),
            contentAlignment = Alignment.Center,
        ) {
            BitnagilIcon(
                id = R.drawable.ic_location,
                tint = null,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CurrentLocationInput(
        currentLocation = null,
        onClick = {},
    )
}
