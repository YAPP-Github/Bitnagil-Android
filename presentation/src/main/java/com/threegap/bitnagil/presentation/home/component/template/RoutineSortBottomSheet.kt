package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.threegap.bitnagil.presentation.home.model.RoutineSortType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineSortBottomSheet(
    currentSortType: RoutineSortType,
    onSortTypeChange: (RoutineSortType) -> Unit,
    onDismiss: () -> Unit,
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
        SortOption(
            currentSortType = currentSortType,
            onClick = { sortType ->
                onSortTypeChange(sortType)
                coroutineScope
                    .launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 18.dp),
        )
    }
}

@Composable
private fun SortOption(
    currentSortType: RoutineSortType,
    onClick: (RoutineSortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sortOptions = listOf(
        "완료한 루틴 순" to RoutineSortType.COMPLETED_FIRST,
        "미완료한 루틴 순" to RoutineSortType.INCOMPLETE_FIRST,
    )

    Column(modifier = modifier) {
        sortOptions.forEachIndexed { index, (text, sortType) ->
            SortOptionItem(
                text = text,
                sortType = sortType,
                isSelected = currentSortType == sortType,
                onClick = onClick,
            )

            if (index < sortOptions.lastIndex) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = BitnagilTheme.colors.coolGray97,
                )
            }
        }
    }
}

@Composable
private fun SortOptionItem(
    text: String,
    sortType: RoutineSortType,
    isSelected: Boolean,
    onClick: (RoutineSortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(36.dp)
            .clickableWithoutRipple { onClick(sortType) },
    ) {
        Text(
            text = text,
            color = BitnagilTheme.colors.black,
            style = BitnagilTheme.typography.body1Regular,
            modifier = Modifier.weight(1f),
        )

        if (isSelected) {
            BitnagilIcon(
                id = R.drawable.ic_check,
                tint = BitnagilTheme.colors.orange500,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SortOptionsPreview() {
    SortOption(
        currentSortType = RoutineSortType.COMPLETED_FIRST,
        onClick = {},
    )
}
