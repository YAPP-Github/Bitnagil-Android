package com.threegap.bitnagil.presentation.screen.onboarding.component.template

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.threegap.bitnagil.presentation.screen.onboarding.model.OnBoardingItemUiModel

@Preview(showBackground = true)
@Composable
fun OnBoardingTemplatePreview() {
    OnBoardingSelectTemplate(
        title = "title",
        subText = "subText",
        items = listOf(
            OnBoardingItemUiModel(
                id = "item1",
                title = "title1",
                description = "description1",
            ),
            OnBoardingItemUiModel(
                id = "item2",
                title = "title2",
                description = "description2",
            ),
            OnBoardingItemUiModel(
                id = "item3",
                title = "title3",
                description = "description3",
            ),
            OnBoardingItemUiModel(
                id = "item4",
                title = "title4",
                description = "description4",
            ),
        ),
        onClickNextButton = {},
        onClickItem = {},
        onClickSkip = {},
    )
}
