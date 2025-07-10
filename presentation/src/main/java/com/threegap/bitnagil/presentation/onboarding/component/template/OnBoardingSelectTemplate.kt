package com.threegap.bitnagil.presentation.onboarding.component.template

import OnBoardingSelectButton
import TextButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem

@Composable
fun OnBoardingSelectTemplate(
    modifier: Modifier = Modifier,
    title: String,
    subText: String? = null,
    items: List<OnBoardingItem>,
    nextButtonEnable: Boolean = false,
    onClickNextButton: () -> Unit,
) {
    val titleTextStyle = TextStyle(fontSize = 20.sp)
    val subTextStyle = TextStyle(fontSize = 14.sp)

    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 32.dp),
    ) {
        Text(title, style = titleTextStyle)

        subText?.let {
            Spacer(modifier = Modifier.height(10.dp))

            Text(subText, style = subTextStyle)
        }

        Spacer(modifier = Modifier.height(28.dp))

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = scrollState),
        ) {
            for (item in items) {
                OnBoardingSelectButton(
                    modifier = Modifier.padding(bottom = 12.dp),
                    title = item.title,
                    description = item.description ?: "",
                    onClick = { },
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            text = "다음",
            onClick = onClickNextButton,
            enabled = nextButtonEnable,
        )
    }
}
