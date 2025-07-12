package com.threegap.bitnagil.presentation.terms.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                .clickable(onClick = onCheckedChange),
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (isChecked) Color.Black else Color.Gray,
                modifier = Modifier.size(16.dp),
            )
            Text(text = title)
        }

        if (showMore) {
            Text(
                text = "더보기",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = onClickShowMore),
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
