package com.threegap.bitnagil.presentation.recommendroutine.component.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun RecommendCategoryChip(
    categoryName: String,
    isSelected: Boolean,
    onCategorySelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = if (!isSelected) BitnagilTheme.colors.coolGray99 else BitnagilTheme.colors.navy500,
                shape = RoundedCornerShape(20.dp),
            )
            // todo: 리플효과 제거하기
            .clickable { onCategorySelected() }
            .padding(
                vertical = 9.dp,
                horizontal = 14.dp,
            ),
    ) {
        Text(
            text = categoryName,
            color = if (!isSelected) BitnagilTheme.colors.navy100 else BitnagilTheme.colors.white,
            style = if (!isSelected) BitnagilTheme.typography.caption1Regular else BitnagilTheme.typography.caption1SemiBold,
        )
    }
}

@Preview
@Composable
private fun RecommendCategoryChipStatesPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RecommendCategoryChip(
            categoryName = "맞춤 추천",
            isSelected = true,
            onCategorySelected = {},
        )

        RecommendCategoryChip(
            categoryName = "나가봐요",
            isSelected = false,
            onCategorySelected = {},
        )
    }
}
