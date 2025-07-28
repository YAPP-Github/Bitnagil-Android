package com.threegap.bitnagil.presentation.terms.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun ToggleAllAgreementsItem(
    isAllAgreed: Boolean,
    onToggleAllAgreements: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isAllAgreed) BitnagilTheme.colors.lightBlue75 else BitnagilTheme.colors.coolGray99
    val iconColor = if (isAllAgreed) BitnagilTheme.colors.navy500 else BitnagilTheme.colors.navy100
    val textColor = if (isAllAgreed) BitnagilTheme.colors.navy500 else BitnagilTheme.colors.coolGray50

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp),
            )
            .clickableWithoutRipple { onToggleAllAgreements(!isAllAgreed) }
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
    ) {
        BitnagilIcon(
            id = R.drawable.ic_check,
            tint = iconColor,
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = "전체동의",
            color = textColor,
            style = BitnagilTheme.typography.subtitle1SemiBold,
        )
    }
}

@Preview
@Composable
private fun ToggleAllAgreementsItemPreview() {
    var isAllAgreed by remember { mutableStateOf(false) }

    ToggleAllAgreementsItem(
        isAllAgreed = isAllAgreed,
        onToggleAllAgreements = {
            isAllAgreed = !isAllAgreed
        },
    )
}
