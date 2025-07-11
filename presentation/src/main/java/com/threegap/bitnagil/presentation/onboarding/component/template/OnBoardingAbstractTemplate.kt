package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun OnBoardingAbstractTemplate(
    modifier: Modifier = Modifier,
    title: String,
    moveToNext: () -> Unit,
    subText: @Composable ColumnScope.() -> Unit,
) {
    LaunchedEffect(Unit) {
        delay(2000L)
        moveToNext()
    }

    val titleTextStyle = TextStyle(fontSize = 20.sp)

    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 32.dp),
    ) {
        Text(title, style = titleTextStyle)

        Spacer(modifier = Modifier.height(10.dp))

        subText()

        Spacer(modifier = Modifier.height(36.dp))

        Box(modifier = Modifier.weight(1f))
    }
}
