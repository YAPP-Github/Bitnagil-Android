package com.threegap.bitnagil.presentation.home.component.atom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.home.model.EmotionBallType

@Composable
fun EmotionBall(
    emotionType: EmotionBallType?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (emotionType != null) {
        Image(
            painter = painterResource(emotionType.drawableId),
            contentDescription = null,
            modifier = modifier
                .size(172.dp)
                .clickableWithoutRipple { onClick() }
                .padding(10.dp)
                .fillMaxSize()
                .shadow(
                    elevation = 24.dp,
                    shape = CircleShape,
                    ambientColor = emotionType.ambientColor,
                    spotColor = emotionType.spotColor
                ),
        )
    } else {
        Image(
            painter = painterResource(R.drawable.default_ball),
            contentDescription = null,
            modifier = modifier
                .size(172.dp)
                .clickableWithoutRipple { onClick() }
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true, name = "Default (Null)")
@Composable
private fun EmotionBallDefaultPreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmotionBall(
            emotionType = null,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Calm")
@Composable
private fun EmotionBallCalmPreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmotionBall(
            emotionType = EmotionBallType.CALM,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Vitality")
@Composable
private fun EmotionBallVitalityPreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmotionBall(
            emotionType = EmotionBallType.VITALITY,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Lethargy")
@Composable
private fun EmotionBallLethargyPreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmotionBall(
            emotionType = EmotionBallType.LETHARGY,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Anxiety")
@Composable
private fun EmotionBallAnxietyPreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmotionBall(
            emotionType = EmotionBallType.ANXIETY,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Satisfaction")
@Composable
private fun EmotionBallSatisfactionPreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmotionBall(
            emotionType = EmotionBallType.SATISFACTION,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Fatigue")
@Composable
private fun EmotionBallFatiguePreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmotionBall(
            emotionType = EmotionBallType.FATIGUE,
            onClick = {}
        )
    }
}
