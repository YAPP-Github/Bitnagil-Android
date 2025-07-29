package com.threegap.bitnagil.designsystem.component.atom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun BitnagilCheckBox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    colors: BitnagilCheckBoxColor = BitnagilCheckBoxColor.terms(),
) {
    val iconColor = if (checked) colors.checkedColor else colors.uncheckedColor

    Box(
        modifier = modifier
            .let {
                if (onCheckedChange != null) {
                    it.clickableWithoutRipple { onCheckedChange(!checked) }
                } else {
                    it
                }
            }
            .background(
                color = BitnagilTheme.colors.white,
                shape = RoundedCornerShape(6.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_check),
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = modifier.size(24.dp),
        )
    }
}

data class BitnagilCheckBoxColor(
    val uncheckedColor: Color,
    val checkedColor: Color,
) {
    companion object {
        @Composable
        fun terms(): BitnagilCheckBoxColor = BitnagilCheckBoxColor(
            uncheckedColor = BitnagilTheme.colors.navy50,
            checkedColor = BitnagilTheme.colors.navy500,
        )
    }
}

@Preview
@Composable
private fun BitnagilCheckBoxPreview() {
    var checked by remember { mutableStateOf(false) }
    BitnagilCheckBox(
        checked = checked,
        onCheckedChange = { checked = !checked },
    )
}
