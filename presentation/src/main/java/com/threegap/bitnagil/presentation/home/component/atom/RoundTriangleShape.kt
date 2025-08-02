package com.threegap.bitnagil.presentation.home.component.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun roundedTriangleShape(cornerRadius: Float) =
    GenericShape { size, _ ->
        val width = size.width
        val height = size.height
        val radius = cornerRadius.coerceAtMost(minOf(width, height) * 0.3f)

        moveTo(0f, 0f)
        lineTo(width, 0f)

        lineTo(width / 2 + radius, height - radius)
        quadraticTo(
            width / 2,
            height,
            width / 2 - radius,
            height - radius,
        )

        lineTo(0f, 0f)
        close()
    }

@Preview
@Composable
private fun RoundedTriangleShapePreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(120.dp, 100.dp)
                .clip(roundedTriangleShape(20f))
                .background(Color.Green),
        )
    }
}
