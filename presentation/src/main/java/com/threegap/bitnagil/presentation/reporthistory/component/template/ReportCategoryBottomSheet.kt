package com.threegap.bitnagil.presentation.reporthistory.component.template

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.presentation.common.extension.displayExamples
import com.threegap.bitnagil.presentation.common.extension.displayTitle
import com.threegap.bitnagil.presentation.common.extension.iconRes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportCategoryBottomSheet(
    selectedCategory: ReportCategory?,
    onDismiss: () -> Unit,
    onSelected: (ReportCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = BitnagilTheme.colors.white,
        contentColor = BitnagilTheme.colors.white,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 28.dp),
        ) {
            ReportCategory.entries.forEachIndexed { index, category ->
                ReportCategoryItem(
                    icon = category.iconRes,
                    title = category.displayTitle,
                    description = category.displayExamples,
                    isSelected = selectedCategory == category,
                    onClick = {
                        onSelected(category)
                        coroutineScope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) onDismiss()
                            }
                    },
                )

                if (index < ReportCategory.entries.lastIndex) {
                    HorizontalDivider(
                        color = BitnagilTheme.colors.coolGray97,
                    )
                }
            }
        }
    }
}

@Composable
private fun ReportCategoryItem(
    title: String,
    description: String,
    @DrawableRes icon: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickableWithoutRipple { onClick() }
            .padding(vertical = 14.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BitnagilIcon(id = icon, tint = null)

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1Medium,
            )

            Text(
                text = description,
                color = BitnagilTheme.colors.coolGray70,
                style = BitnagilTheme.typography.body2Medium,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (isSelected) {
            BitnagilIcon(
                id = R.drawable.ic_check_md,
                tint = BitnagilTheme.colors.orange500,
            )
        }
    }
}

@Preview
@Composable
private fun ReportCategoryBottomSheetPreview() {
    ReportCategoryBottomSheet(
        selectedCategory = ReportCategory.WATERFACILITY,
        onDismiss = {},
        onSelected = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ReportCategoryItemPreview() {
    ReportCategoryItem(
        title = "교통 시설",
        description = "어쩌구",
        icon = R.drawable.ic_check_md,
        isSelected = true,
        onClick = {},
    )
}
