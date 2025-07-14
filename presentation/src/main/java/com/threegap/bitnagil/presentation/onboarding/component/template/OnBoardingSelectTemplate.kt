package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.threegap.bitnagil.presentation.onboarding.component.atom.textbutton.TextButton
import com.threegap.bitnagil.presentation.onboarding.component.block.selectbutton.OnBoardingSelectButton
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem

@Composable
fun OnBoardingSelectTemplate(
    modifier: Modifier = Modifier,
    title: String,
    subText: String? = null,
    items: List<OnBoardingItem>,
    nextButtonEnable: Boolean = false,
    onClickNextButton: () -> Unit,
    onClickItem: (String) -> Unit,
    onClickSkip: (() -> Unit)? = null,
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
                    description = item.description,
                    onClick = {
                        onClickItem(item.id)
                    },
                    selected = item.selected,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            text = "다음",
            onClick = onClickNextButton,
            enabled = nextButtonEnable,
        )

        onClickSkip?.let {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onClickSkip,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "건너뛰기",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
