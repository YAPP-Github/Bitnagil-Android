package com.threegap.bitnagil.designsystem.component.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun BitnagilChip(
    title: String,
    isSelected: Boolean,
    onCategorySelected: () -> Unit,
    modifier: Modifier = Modifier,
    count: Int? = null,
) {
    val chipTitle = if (count == null) title else "$title $count"

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = if (!isSelected) BitnagilTheme.colors.white else BitnagilTheme.colors.coolGray10,
                shape = RoundedCornerShape(20.dp),
            )
            .height(36.dp)
            .clickableWithoutRipple(onClick = onCategorySelected)
            .padding(
                vertical = 6.dp,
                horizontal = 14.dp,
            ),
    ) {
        Text(
            text = chipTitle,
            color = if (!isSelected) BitnagilTheme.colors.coolGray60 else BitnagilTheme.colors.white,
            style = if (!isSelected) BitnagilTheme.typography.caption1Medium else BitnagilTheme.typography.caption1SemiBold,
        )
    }
}

@Preview
@Composable
private fun RecommendCategoryChipStatesPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        BitnagilChip(
            title = "맞춤 추천",
            isSelected = true,
            onCategorySelected = {},
        )

        BitnagilChip(
            title = "나가봐요",
            isSelected = false,
            onCategorySelected = {},
            count = 1,
        )
    }
}
