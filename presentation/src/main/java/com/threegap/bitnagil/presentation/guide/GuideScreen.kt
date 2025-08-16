package com.threegap.bitnagil.presentation.guide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.guide.component.atom.GuideButton
import com.threegap.bitnagil.presentation.guide.component.template.GuideBottomSheet
import com.threegap.bitnagil.presentation.guide.model.GuideIntent
import com.threegap.bitnagil.presentation.guide.model.GuideSideEffect
import com.threegap.bitnagil.presentation.guide.model.GuideType

@Composable
fun GuideScreenContainer(
    navigateToBack: () -> Unit,
    viewModel: GuideViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            is GuideSideEffect.NavigateToBack -> navigateToBack()
        }
    }

    if (uiState.guideBottomSheetVisible) {
        uiState.guideType?.let { guideType ->
            GuideBottomSheet(
                guideType = guideType,
                onDismissRequest = { viewModel.sendIntent(GuideIntent.OnHideGuideBottomSheet) },
            )
        }
    }

    GuideScreen(
        onClickGuideButton = { viewModel.sendIntent(GuideIntent.OnClickGuideButton(it)) },
        onBackClick = { viewModel.sendIntent(GuideIntent.OnBackClick) },
    )
}

@Composable
private fun GuideScreen(
    onClickGuideButton: (GuideType) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.white)
            .statusBarsPadding(),
    ) {
        BitnagilTopBar(
            title = "설명서",
            showBackButton = true,
            onBackClick = onBackClick,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(46.dp))

        GuideType.entries.forEach { guideType ->
            GuideButton(
                title = guideType.title,
                onClick = { onClickGuideButton(guideType) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp),
            )
        }
    }
}

@Preview
@Composable
private fun GuideScreenPreview() {
    GuideScreen(
        onClickGuideButton = {},
        onBackClick = {},
    )
}
