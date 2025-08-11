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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton

@Composable
fun RoutineDetailsCard(
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                id = R.drawable.ic_wakeup,
                tint = null,
                modifier = Modifier
                    .background(
                        color = BitnagilTheme.colors.orange25,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(4.dp),
            )

            Text(
                text = "개운하게 일어나기",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1SemiBold,
                modifier = Modifier.padding(start = 10.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            BitnagilIconButton(
                id = R.drawable.ic_edit,
                onClick = { /*TODO*/ },
                tint = null,
                paddingValues = PaddingValues(12.dp),
            )

            BitnagilIconButton(
                id = R.drawable.ic_trash,
                onClick = onDeleteClick,
                tint = null,
                paddingValues = PaddingValues(12.dp),
            )
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
                text = "세부 루틴",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
            )
            Text(
                text = "•  어쩌구",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
            )
            Text(
                text = "•  어쩌구",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
            )
            Text(
                text = "•  어쩌구",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
            )
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
                text = "반복:",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
            )
            Text(
                text = "기간:",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
            )
            Text(
                text = "시간:",
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
            )
        }
    }
}

@Preview
@Composable
private fun RoutineDetailsCardPreview() {
    RoutineDetailsCard(
        onDeleteClick = {},
    )
}
