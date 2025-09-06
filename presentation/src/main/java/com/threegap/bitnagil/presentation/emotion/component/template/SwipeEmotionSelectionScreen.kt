package com.threegap.bitnagil.presentation.emotion.component.template

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.common.dimension.dpToPx
import com.threegap.bitnagil.presentation.emotion.component.atom.EmotionMarbleImage
import com.threegap.bitnagil.presentation.emotion.model.EmotionImageUiModel
import com.threegap.bitnagil.presentation.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.emotion.model.EmotionUiModel
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.max

@Composable
fun SwipeEmotionSelectionScreen(
    state: EmotionState,
    onClickPreviousButton: () -> Unit,
    onSelectEmotion: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.white)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BitnagilTopBar(
            showBackButton = true,
            title = "오늘 감정 등록하기",
            onBackClick = onClickPreviousButton,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            state.emotionTypeUiModels.forEach { emotion ->
                EmotionMarbleImage(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = BitnagilTheme.colors.coolGray98),
                    image = emotion.image,
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFEFECFF), shape = RoundedCornerShape(12.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "평온함은 마음이 고요하고 편안해\n균형을 이루는 상태에요",
                    style = BitnagilTheme.typography.cafe24SsurroundAir2.copy(color = Color(0xFF692BD0)),
                    textAlign = TextAlign.Center,
                )
            }

            Canvas(
                modifier = Modifier
                    .height(10.dp)
                    .width(24.dp),
            ) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width / 2 , size.height)
                    lineTo(0f, 0f)
                    close()
                }
                drawPath(path, color = Color(0xFFEFECFF))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.weight(1f),
        ) {
            EmotionPager(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f),
                emotions = state.emotionTypeUiModels,
                enabled = !state.isLoading,
                onSelectEmotion = onSelectEmotion,
            )

            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GestureDescriptionText(
                    currentEmotionSelectable = state.emotionTypeUiModels.any { it.selectable },
                )

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    painter = painterResource(R.drawable.img_pomo_hand),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .width(263.dp)
                        .height(192.dp),
                )
            }

            Image(
                painter = painterResource(R.drawable.img_pomo_thumb),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(3f)
                    .offset(x = (-20).dp, y = (-70).dp),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun GestureDescriptionText(
    currentEmotionSelectable: Boolean,
) {
    if (currentEmotionSelectable) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("선택한 감정 구슬을 아래로 놓아주세요", style = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.coolGray50))

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(R.drawable.ic_double_down_arrow_24),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(26.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_double_left_arrow_24),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )

            Text(
                "좌우로 스와이프해\n감정 구슬을 골라주세요",
                style = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.coolGray50),
                textAlign = TextAlign.Center,
            )

            Image(
                painter = painterResource(R.drawable.ic_double_right_arrow_24),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun EmotionPager(
    modifier: Modifier = Modifier,
    emotions: List<EmotionUiModel>,
    enabled: Boolean,
    onSelectEmotion: (String) -> Unit,
) {
    val actualItemCount = max(1, emotions.size)

    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % actualItemCount),
        pageCount = { Int.MAX_VALUE },
    )

    var showText by remember { mutableStateOf(true) }
    val currentItem = emotions[pagerState.currentPage % actualItemCount]

    LaunchedEffect(pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress) {
            showText = false
        } else {
            delay(500)
            showText = true
        }
    }

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        val density = LocalDensity.current
        val screenWidth = with(density) { constraints.maxWidth.toDp() }

        val itemWidth = 140.dp
        val contentPadding = (screenWidth - itemWidth) / 2
        val pageSpacing = ((screenWidth - itemWidth * 2) / 2)

        HorizontalPager(
            modifier = Modifier.fillMaxHeight(),
            state = pagerState,
            pageSpacing = pageSpacing,
            contentPadding = PaddingValues(horizontal = contentPadding),
            verticalAlignment = Alignment.Top,
        ) { page ->
            val itemIndex = page % actualItemCount
            EmotionPagerItem(
                emotion = emotions[itemIndex],
                pagerState = pagerState,
                page = page,
                size = itemWidth,
                maximumDraggableYOffset = constraints.maxHeight - 280.dp.dpToPx(),
                enabled = enabled,
                onSelectEmotion = onSelectEmotion,
            )
        }

        AnimatedVisibility(
            visible = showText,
            enter = fadeIn(animationSpec = tween(150)),
            exit = fadeOut(animationSpec = tween(50)),
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0x99E1D5FA), shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = currentItem.emotionMarbleName,
                    style = BitnagilTheme.typography.title3SemiBold,
                    color = Color(0xFF692BD0),
                )
            }
        }
    }
}

@Composable
private fun EmotionPagerItem(
    emotion: EmotionUiModel,
    pagerState: PagerState,
    page: Int,
    size: Dp,
    maximumDraggableYOffset: Float = Float.MAX_VALUE,
    enabled: Boolean,
    onSelectEmotion: (String) -> Unit,
) {
    val pageOffset = (
        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        ).absoluteValue

    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    EmotionMarbleImage(
        image = emotion.image,
        modifier = Modifier
            .size(size)
            .aspectRatio(1f)
            .graphicsLayer {
                translationY = lerp(size.value * 1f, 0f, pageOffset)
            }
            .offset {
                IntOffset(0, offsetY.value.toInt())
            }
            .draggable(
                orientation = Orientation.Vertical,
                enabled = (pageOffset == 0f && enabled),
                state = rememberDraggableState { deltaY ->
                    coroutineScope.launch {
                        val newOffsetY = offsetY.value + deltaY
                        if (newOffsetY in 0f..maximumDraggableYOffset) {
                            offsetY.snapTo(newOffsetY)
                        }
                    }
                },
                onDragStopped = {
                    coroutineScope.launch {
                        val targetOffsetY = if (offsetY.value >= maximumDraggableYOffset * 0.8) {
                            onSelectEmotion(emotion.emotionType)
                            maximumDraggableYOffset
                        } else {
                            0f
                        }

                        offsetY.animateTo(
                            targetValue = targetOffsetY,
                            animationSpec = tween(durationMillis = 300)
                        )
                    }
                },
            )
            .background(color = BitnagilTheme.colors.coolGray98),
    )
}

@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun Preview() {
    BitnagilTheme {
        SwipeEmotionSelectionScreen(
            state = EmotionState(
                emotionTypeUiModels = listOf(
                    EmotionUiModel(
                        emotionType = "emotionType",
                        emotionMarbleName = "구슬 이름 1",
                        image = EmotionImageUiModel.Url(
                            url = "https://bitnagil-s3.s3.ap-northeast-2.amazonaws.com/home_satisfaction.png",
                            offlineBackupImageResourceId = null,
                        ),
                    ),
                    EmotionUiModel(
                        emotionType = "emotionType",
                        emotionMarbleName = "구슬 이름 2",
                        image = EmotionImageUiModel.Url(
                            url = "https://bitnagil-s3.s3.ap-northeast-2.amazonaws.com/home_satisfaction.png",
                            offlineBackupImageResourceId = null,
                        ),
                    ),
                    EmotionUiModel.Default
                ),
                isLoading = false,
                step = EmotionScreenStep.Emotion,
                recommendRoutines = listOf(),
            ),
            onClickPreviousButton = {},
            onSelectEmotion = {},
        )
    }
}
