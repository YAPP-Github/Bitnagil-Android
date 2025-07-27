package com.threegap.bitnagil.presentation.terms.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun TermsAgreementItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    showMore: Boolean = false,
    onClickShowMore: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(
                vertical = 10.dp,
                horizontal = 20.dp,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickableWithoutRipple { onCheckedChange() },
        ) {
            BitnagilIcon(
                id = R.drawable.ic_check,
                tint = if (isChecked) BitnagilTheme.colors.navy500 else BitnagilTheme.colors.navy100,
            )

            Text(
                text = title,
                color = BitnagilTheme.colors.coolGray50,
                style = BitnagilTheme.typography.body2Medium
            )
        }

        if (showMore) {
            Text(
                text = "더보기",
                color = BitnagilTheme.colors.coolGray50,
                style = BitnagilTheme.typography.caption1UnderlineSemiBold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickableWithoutRipple { onClickShowMore() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TermsAgreementItemPreview() {
    var isChecked by remember { mutableStateOf(false) }

    TermsAgreementItem(
        title = "(필수) 서비스 이용약관 동의",
        onCheckedChange = {
            isChecked = !isChecked
        },
        isChecked = isChecked,
        showMore = true,
    )
}
