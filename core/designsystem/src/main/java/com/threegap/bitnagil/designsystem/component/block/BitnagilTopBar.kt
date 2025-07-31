package com.threegap.bitnagil.designsystem.component.block

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun BitnagilTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .height(54.dp),
    ) {
        if (showBackButton) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 4.dp)
                    .clickableWithoutRipple { onBackClick() },
            ) {
                BitnagilIcon(
                    id = R.drawable.ic_back_arrow_36,
                )
            }
        }

        if (title != null) {
            Text(
                text = title,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.title3SemiBold,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column {
        BitnagilTopBar(title = "추천루틴")

        HorizontalDivider()

        BitnagilTopBar(
            title = "설정",
            showBackButton = true,
            onBackClick = {},
        )

        HorizontalDivider()

        BitnagilTopBar(
            title = "마이페이지",
            actions = {
                BitnagilIcon(
                    id = R.drawable.ic_setting,
                    modifier = Modifier.padding(6.dp),
                )
            },
        )
    }
}
