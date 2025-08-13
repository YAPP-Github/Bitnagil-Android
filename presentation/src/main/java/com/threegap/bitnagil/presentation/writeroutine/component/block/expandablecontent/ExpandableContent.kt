import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton

@Composable
fun ExpandableContent(
    expand: Boolean,
    required: Boolean,
    iconResourceId: Int,
    title: String,
    placeHolder: String,
    valueText: String?,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val showValueText = !valueText.isNullOrEmpty()
    val mainTextStyle = BitnagilTheme.typography.body1SemiBold.copy(color = BitnagilTheme.colors.coolGray10)
    val subTextStyle = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.coolGray70)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color = BitnagilTheme.colors.coolGray99)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 12.dp, top = 18.dp, bottom = 18.dp)
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(iconResourceId),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = title,
                        style = if (showValueText && !expand) subTextStyle else mainTextStyle,
                    )

                    if (required)
                        BitnagilIcon(
                            id = R.drawable.ic_routine_success,
                            tint = null,
                            modifier = Modifier.size(12.dp),
                        )
                }
                if (!expand) {
                    Text(
                        text = if (showValueText) valueText ?: placeHolder else placeHolder,
                        style = if (showValueText) mainTextStyle else subTextStyle,
                    )
                }
            }

            BitnagilIconButton(
                id = if (expand) R.drawable.ic_up_arrow_20 else R.drawable.ic_down_arrow_20,
                tint = BitnagilTheme.colors.coolGray30,
                modifier = Modifier
                    .size(36.dp)
                    .padding(8.dp),
                onClick = onClick,
            )
        }

        AnimatedVisibility(visible = expand) {
            Column{
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
                    color = BitnagilTheme.colors.coolGray96,
                    thickness = 1.dp,
                )

                content()
            }
        }
    }

}

@Preview(heightDp = 300)
@Composable
fun ExpandableContentPreview() {
    var isExpanded by remember { mutableStateOf(true) }
    var isChecked by remember { mutableStateOf(false) }

    Column {
        ExpandableContent(
            expand = isExpanded,
            iconResourceId = R.drawable.img_subroutines,
            title = "세부루틴",
            placeHolder = "세부루틴을 설정해주세요.",
            valueText = "이거\n길면\n어떻게될까",
            onClick = { isExpanded = !isExpanded },
            required = true,
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(100.dp).clickable { isChecked = !isChecked },
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Gray,
                            uncheckedColor = Color.Gray,
                        ),
                    )
                    Text(
                        text = "세부루틴 설정 안 함",
                        color = Color.Gray,
                        fontSize = 14.sp,
                    )
                }
            },
        )
    }
}
