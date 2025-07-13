package com.threegap.bitnagil.presentation.onboarding.component.atom.textbutton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, name = "Disabled State", widthDp = 300)
@Composable
fun TextButtonPreview() {
    Column(Modifier.padding(16.dp)) {
        TextButton(text = "시작하기", onClick = {}, enabled = false)

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(text = "시작하기", onClick = {}, enabled = true)
    }
}
