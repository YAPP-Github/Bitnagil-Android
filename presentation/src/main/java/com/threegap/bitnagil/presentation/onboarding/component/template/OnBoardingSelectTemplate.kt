package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilSelectButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor
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
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 32.dp),
    ) {
        Text(
            text = title,
            color = BitnagilTheme.colors.navy500,
            style = BitnagilTheme.typography.title2Bold,
        )

        subText?.let {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = subText,
                color = BitnagilTheme.colors.coolGray50,
                style = BitnagilTheme.typography.body2Medium,
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = scrollState),
        ) {
            for (item in items) {
                BitnagilSelectButton(
                    title = item.title,
                    description = item.description,
                    onClick = {
                        onClickItem(item.id)
                    },
                    selected = item.selected,
                    modifier = Modifier.padding(bottom = 12.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        BitnagilTextButton(
            text = "다음",
            onClick = onClickNextButton,
            enabled = nextButtonEnable,
        )

        onClickSkip?.let {
            Spacer(modifier = Modifier.height(10.dp))

            BitnagilTextButton(
                text = "건너뛰기",
                onClick = onClickSkip,
                colors = BitnagilTextButtonColor.skip(),
                textStyle = BitnagilTheme.typography.body2Regular,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}
