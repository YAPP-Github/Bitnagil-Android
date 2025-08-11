package com.threegap.bitnagil.presentation.routinelist.component.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        contentColor = BitnagilTheme.colors.white,
        containerColor = BitnagilTheme.colors.white,
    ) {
        RepeatRoutineDeleteContent(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 26.dp),
        )
    }
}

@Composable
fun RepeatRoutineDeleteContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "이 루틴은 반복 설정되어 있어요",
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.title3SemiBold,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "오늘만 삭제하거나, 전체 반복 일정에서 모두 삭제할 수\n있습니다. 삭제한 루틴은 되돌릴 수 없어요.",
            color = BitnagilTheme.colors.coolGray40,
            style = BitnagilTheme.typography.body2Medium,
            modifier = Modifier
                .padding(end = 40.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        BitnagilTextButton(
            text = "오늘만 삭제",
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
            textStyle = BitnagilTheme.typography.body2Medium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        BitnagilTextButton(
            text = "모든 날짜에서 삭제",
            onClick = { /*TODO*/ },
            colors = BitnagilTextButtonColor.delete(),
            modifier = Modifier.fillMaxWidth(),
            textStyle = BitnagilTheme.typography.body2Medium,
        )
    }
}

@Composable
fun SingleRoutineDeleteContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "루틴을 삭제하시겠어요?",
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.title3SemiBold,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "이 루틴과 관련된 모든 기록이 함께 삭제되며, 삭제 후에는\n되돌릴 수 없습니다. 정말 삭제하시겠어요?",
            color = BitnagilTheme.colors.coolGray40,
            style = BitnagilTheme.typography.body2Medium,
            modifier = Modifier
                .padding(end = 40.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            BitnagilTextButton(
                text = "취소",
                onClick = { /*TODO*/ },
                colors = BitnagilTextButtonColor.cancel(),
                modifier = Modifier.weight(1f),
                textStyle = BitnagilTheme.typography.body2Medium,
            )

            BitnagilTextButton(
                text = "모든 날짜에서 삭제",
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f),
                textStyle = BitnagilTheme.typography.body2Medium,
            )
        }
    }
}

@Preview
@Composable
private fun DeleteConfirmBottomSheetPreview() {
    DeleteConfirmBottomSheet(
        onDismissRequest = {},
    )
}
