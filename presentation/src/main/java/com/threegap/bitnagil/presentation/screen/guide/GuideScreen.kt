package com.threegap.bitnagil.presentation.screen.guide

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
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.screen.guide.component.atom.GuideButton
import com.threegap.bitnagil.presentation.screen.guide.component.template.GuideBottomSheet
import com.threegap.bitnagil.presentation.screen.guide.contract.GuideSideEffect
import com.threegap.bitnagil.presentation.screen.guide.model.GuideType
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun GuideScreenContainer(
    navigateToBack: () -> Unit,
    viewModel: GuideViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is GuideSideEffect.NavigateToBack -> navigateToBack()
        }
    }

    if (uiState.guideBottomSheetVisible) {
        uiState.guideType?.let { guideType ->
            GuideBottomSheet(
                guideType = guideType,
                onDismissRequest = viewModel::onHideGuideBottomSheet,
            )
        }
    }

    GuideScreen(
        onClickGuideButton = viewModel::onShowGuideBottomSheet,
        onBackClick = viewModel::navigateToBack,
    )
}

@Composable
private fun GuideScreen(
    onClickGuideButton: (GuideType) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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

@Preview(showBackground = true)
@Composable
private fun GuideScreenPreview() {
    GuideScreen(
        onClickGuideButton = {},
        onBackClick = {},
    )
}
