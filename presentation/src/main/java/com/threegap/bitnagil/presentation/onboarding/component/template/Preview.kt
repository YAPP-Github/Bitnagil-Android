package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem

@Preview(showBackground = true)
@Composable
fun OnBoardingTemplatePreview() {
    OnBoardingSelectTemplate(
        title = "title",
        subText = "subText",
        items = listOf(
            OnBoardingItem(
                id = "item1",
                title = "title1",
                description = "description1",
            ),
            OnBoardingItem(
                id = "item2",
                title = "title2",
                description = "description2",
            ),
            OnBoardingItem(
                id = "item3",
                title = "title3",
                description = "description3",
            ),
            OnBoardingItem(
                id = "item4",
                title = "title4",
                description = "description4",
            ),
        ),
        onClickNextButton = {},
        onClickItem = {},
    )
}
