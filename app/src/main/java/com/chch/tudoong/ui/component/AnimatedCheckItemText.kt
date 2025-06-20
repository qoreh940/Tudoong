package com.chch.mycompose.ui.screen.checklist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun AnimatedCheckItemText(
    text: String,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    val textColor by animateColorAsState(
        targetValue = if (isCompleted)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        else
            MaterialTheme.colorScheme.primary,
        animationSpec = tween(300),
        label = "text_color"
    )

    val textDecoration = if (isCompleted) {
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