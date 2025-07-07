package com.chch.tudoong.presentation.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TudoongCheckbox(
    state: TdCheckboxState,
    onStateChange: ((TdCheckboxState) -> Unit)?,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    colors: TriStateCheckboxColors = TriStateCheckboxDefaults.colors()
)
{
    val animatedBorderColor by animateColorAsState(
        targetValue = when (state) {
            TdCheckboxState.NONE -> colors.uncheckedBorderColor
            TdCheckboxState.DONE -> colors.checkedBorderColor
            TdCheckboxState.MISSED -> colors.missedBorderColor
        },
        animationSpec = tween(100),
        label = "border_color"
    )

    val animatedBackgroundColor by animateColorAsState(
        targetValue = when (state) {
            TdCheckboxState.NONE -> colors.uncheckedBackgroundColor
            TdCheckboxState.DONE -> colors.checkedBackgroundColor
            TdCheckboxState.MISSED -> colors.missedBackgroundColor
        },
        animationSpec = tween(100),
        label = "background_color"
    )

    Box(
        modifier = modifier
            .size(size)
            .background(
                color = animatedBackgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 2.dp,
                color = animatedBorderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onStateChange?.invoke(state.next()) },
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            TdCheckboxState.NONE -> {
                // 빈 상태 - 아무것도 표시하지 않음
            }
            TdCheckboxState.DONE -> {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "완료",
                    tint = colors.checkedIconColor,
                    modifier = Modifier.size(size * 0.7f)
                )
            }
            TdCheckboxState.MISSED -> {
                Icon(
                    imageVector = Icons.Default.Block,
                    contentDescription = "실패",
                    tint = colors.missedIconColor,
                    modifier = Modifier.size(size * 0.7f)
                )
            }
        }
    }
}


// 체크박스 상태 enum
enum class TdCheckboxState {
    NONE,
    DONE,
    MISSED;

    fun next(): TdCheckboxState {
        return when (this) {
            NONE -> DONE
            DONE -> MISSED
            MISSED -> NONE
        }
    }
}

// 색상 설정을 위한 데이터 클래스
@Immutable
data class TriStateCheckboxColors(
    val uncheckedBorderColor: Color,
    val uncheckedBackgroundColor: Color,
    val checkedBorderColor: Color,
    val checkedBackgroundColor: Color,
    val checkedIconColor: Color,
    val missedBorderColor: Color,
    val missedBackgroundColor: Color,
    val missedIconColor: Color
)

// 기본 색상 설정
object TriStateCheckboxDefaults {
    @Composable
    fun colors(
        uncheckedBorderColor: Color = MaterialTheme.colorScheme.outline,
        uncheckedBackgroundColor: Color = Color.Transparent,
        checkedBorderColor: Color = MaterialTheme.colorScheme.primary,
        checkedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        checkedIconColor: Color = MaterialTheme.colorScheme.onPrimary,
        missedBorderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        missedBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        missedIconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ): TriStateCheckboxColors {
        return TriStateCheckboxColors(
            uncheckedBorderColor = uncheckedBorderColor,
            uncheckedBackgroundColor = uncheckedBackgroundColor,
            checkedBorderColor = checkedBorderColor,
            checkedBackgroundColor = checkedBackgroundColor,
            checkedIconColor = checkedIconColor,
            missedBorderColor = missedBorderColor,
            missedBackgroundColor = missedBackgroundColor,
            missedIconColor = missedIconColor
        )
    }
}