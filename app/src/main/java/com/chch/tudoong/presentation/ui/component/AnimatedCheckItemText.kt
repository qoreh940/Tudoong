package com.chch.mycompose.ui.screen.checklist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.chch.tudoong.presentation.ui.component.TdCheckboxState

@Composable
fun AnimatedCheckItemText(
    text: String,
    checkboxState: TdCheckboxState,
    modifier: Modifier = Modifier
) {
    val textColor by animateColorAsState(
        targetValue = when(checkboxState){
            TdCheckboxState.NONE -> MaterialTheme.colorScheme.primary
            TdCheckboxState.DONE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            TdCheckboxState.MISSED -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        },
        animationSpec = tween(300),
        label = "text_color"
    )

    val textDecoration = if (checkboxState == TdCheckboxState.DONE) {
        TextDecoration.LineThrough
    } else {
        TextDecoration.None
    }

    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        textDecoration = textDecoration,
        style = MaterialTheme.typography.bodyLarge
    )
}