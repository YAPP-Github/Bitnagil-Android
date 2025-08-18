package com.threegap.bitnagil.presentation.guide.component.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.presentation.guide.model.GuideType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideBottomSheet(
    guideType: GuideType,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = BitnagilTheme.colors.coolGray99,
        contentColor = BitnagilTheme.colors.coolGray99,
        modifier = modifier,
    ) {
        GuideBottomSheetContent(
            guideType = guideType,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 26.dp),
        )
    }
}

@Composable
private fun GuideBottomSheetContent(
    guideType: GuideType,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = guideType.title,
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.title3SemiBold,
            modifier = Modifier.padding(bottom = 10.dp),
        )

        Text(
            text = guideType.description,
            color = BitnagilTheme.colors.coolGray40,
            style = BitnagilTheme.typography.body2Medium,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(guideType.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(312f / 198f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GuideBottomSheetContentPreview() {
    GuideBottomSheetContent(
        guideType = GuideType.REGISTER,
    )
}
