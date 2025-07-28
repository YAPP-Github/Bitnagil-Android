package com.threegap.bitnagil.presentation.writeroutine.component.atom.tooltipbutton

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.writeroutine.component.atom.tooltipbutton.model.TooltipDirection
import com.threegap.bitnagil.presentation.writeroutine.component.atom.tooltipbutton.model.TooltipPositionProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipButton(
    tooltipText: String,
    tooltipDirection: TooltipDirection = TooltipDirection.Left,
) {
    val tooltipState = rememberTooltipState()
    val coroutineScope = rememberCoroutineScope()
    val tooltipBoxColor = BitnagilTheme.colors.navy400
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }
    val tooltipPositionX = with(LocalDensity.current) { 16.dp.toPx().toInt() }

    TooltipBox(
        positionProvider = remember {
            TooltipPositionProvider(
                direction = tooltipDirection,
                addPositionX = tooltipPositionX,
            )
        },
        tooltip = {
            Column(
                modifier = Modifier.padding(bottom = 2.dp),
            ) {
                Text(
                    tooltipText,
                    modifier = Modifier
                        .background(
                            color = BitnagilTheme.colors.navy400,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .clickable {
                            tooltipState.dismiss()
                        }
                        .padding(10.dp),
                    style = BitnagilTheme.typography.caption1Medium.copy(color = BitnagilTheme.colors.white),
                )

                Canvas(
                    modifier = Modifier
                        .height(8.dp)
                        .width(16.dp)
                        .offset {
                            IntOffset(x = tooltipPositionX + (buttonSize.width / 2) - 8.dp.toPx().toInt(), y = 0)
                        },
                ) {
                    drawTooltipArrow(this, tooltipBoxColor)
                }
            }
        },
        state = tooltipState,
    ) {
        BitnagilIcon(
            id = R.drawable.ic_tooltip,
            tint = BitnagilTheme.colors.navy200,
            modifier = Modifier
                .clickableWithoutRipple {
                    coroutineScope.launch {
                        tooltipState.show()
                    }
                }
                .onGloballyPositioned { coordinate ->
                    buttonSize = coordinate.size
                }
        )
    }
}

private fun DrawScope.drawTooltipArrow(scope: DrawScope, color: Color) {
    val path = Path().apply {
        moveTo(0f, 0f)
        lineTo(size.width / 2, size.height)
        lineTo(size.width, 0f)
        close()
    }
    scope.drawPath(
        path = path,
        color = color,
    )
}

@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
fun ToolTipButtonPreview() {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        TooltipButton(tooltipText = "이것은 툴팁입니다.")
    }
}
