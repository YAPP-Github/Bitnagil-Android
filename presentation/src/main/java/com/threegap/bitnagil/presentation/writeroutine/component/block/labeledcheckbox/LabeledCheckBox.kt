package com.threegap.bitnagil.presentation.writeroutine.component.block.labeledcheckbox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun LabeledCheckBox(
    label: String,
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(
                    shape = RoundedCornerShape(4.dp),
                    color = if (checked) BitnagilTheme.colors.navy500 else BitnagilTheme.colors.coolGray99,
                )
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp),
                    color = if (checked) BitnagilTheme.colors.navy500 else BitnagilTheme.colors.coolGray95,
                ),
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(label, style = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.navy400))
    }
}
