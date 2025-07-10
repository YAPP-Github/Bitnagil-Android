package com.threegap.bitnagil.presentation.onboarding.component.atom.progress

import OnBoardingProgressBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, widthDp = 300)
@Composable
fun OnBoardingProgressBarPreview() {
    var progress by remember { mutableFloatStateOf(0.0f) }

    Column {
        OnBoardingProgressBar(progress = progress, modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "down",
                modifier = Modifier.clickable {
                    progress = 0f
                },
            )

            Text(
                "up",
                modifier = Modifier.clickable {
                    progress = progress + 0.1f
                },
            )
        }
    }
}
