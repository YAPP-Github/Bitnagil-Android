package com.threegap.bitnagil.presentation.routinelist.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.DayOfWeek.Companion.formatRepeatDays
import com.threegap.bitnagil.presentation.routinelist.model.RoutineUiModel
import com.threegap.bitnagil.presentation.routinelist.model.getColor
import com.threegap.bitnagil.presentation.routinelist.model.getIcon

@Composable
fun RoutineDetailsCard(
    routine: RoutineUiModel,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val infoTextStyle = BitnagilTheme.typography.body2Medium.copy(
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None,
        ),
    )

    Column(
        modifier = modifier
            .background(
                color = BitnagilTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            )
            .fillMaxWidth()
            .padding(vertical = 14.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BitnagilIcon(
                id = if (routine.recommendedRoutineType != null) routine.recommendedRoutineType.getIcon() else R.drawable.ic_shine,
                tint = null,
                modifier = Modifier
                    .background(
                        color = if (routine.recommendedRoutineType != null) routine.recommendedRoutineType.getColor() else BitnagilTheme.colors.yellow10,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(4.dp),
            )

            Text(
                text = routine.routineName,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1SemiBold,
                modifier = Modifier.padding(start = 10.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            if (!routine.routineDeletedYn) {
                BitnagilIconButton(
                    id = R.drawable.ic_edit,
                    onClick = onEditClick,
                    tint = null,
                    paddingValues = PaddingValues(12.dp),
                )
            }

            BitnagilIconButton(
                id = R.drawable.ic_trash,
                onClick = onDeleteClick,
                tint = null,
                paddingValues = PaddingValues(12.dp),
            )
        }

        if (routine.subRoutineNames.isNotEmpty()) {
            HorizontalDivider(
                thickness = 1.dp,
                color = BitnagilTheme.colors.coolGray97,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "세부 루틴",
                    color = BitnagilTheme.colors.coolGray40,
                    style = infoTextStyle,
                )

                routine.subRoutineNames.forEach { name ->
                    Text(
                        text = "•  $name",
                        color = BitnagilTheme.colors.coolGray40,
                        style = infoTextStyle,
                    )
                }
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = BitnagilTheme.colors.coolGray97,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = "반복: ${routine.repeatDay.formatRepeatDays()}",
                color = BitnagilTheme.colors.coolGray40,
                style = infoTextStyle,
            )
            Text(
                text = "기간: ${routine.formattedDateRange}",
                color = BitnagilTheme.colors.coolGray40,
                style = infoTextStyle,
            )
            Text(
                text = "시간: ${routine.executionTime}",
                color = BitnagilTheme.colors.coolGray40,
                style = infoTextStyle,
            )
        }
    }
}

@Preview
@Composable
private fun RoutineDetailsCardPreview() {
    RoutineDetailsCard(
        routine = RoutineUiModel(
            routineId = "1",
            routineName = "야무진 루틴",
            repeatDay = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
            executionTime = "00:00:00",
            routineDate = "2025-08-15",
            startDate = "2025-08-15",
            endDate = "2025-08-25",
            routineDeletedYn = false,
            subRoutineNames = listOf("어쩌구", "저쩌구", "얼씨구"),
            recommendedRoutineType = null,
        ),
        onEditClick = {},
        onDeleteClick = {},
    )
}
