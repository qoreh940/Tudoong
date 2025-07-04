package com.chch.tudoong.presentation.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider

@Composable
fun SettingsPopover( // ColumnPopover, only for project settings
    list: List<SettingItem>,
    dismiss: () -> Unit
) {

    var popupSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val containerColor = MaterialTheme.colorScheme.primaryContainer
    val anchorHeight = 10.dp

    BackHandler {
        dismiss.invoke()
    }


    Popup(
        popupPositionProvider = SettingPopoverPositionProvider(),
        onDismissRequest = dismiss
    ) {

        Column(
            modifier = Modifier
                .onSizeChanged {
                    popupSize = it
                }
        ) {

            //Draw Anchor
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .drawBehind {
                        val width = 12.dp.toPx()
                        val height = anchorHeight.toPx()
                        val popupWidth = popupSize.width.dp.value
                        val startX = popupWidth - 24.dp.toPx()
                        val inputPortPath = Path().apply {
                            moveTo(startX, 0f)
                            lineTo(startX - width / 2, height)
                            lineTo(startX + width / 2, height)
                            lineTo(startX, 0f)
                            close()
                        }

                        drawIntoCanvas { c ->
                            c.drawOutline(
                                outline = Outline.Generic(inputPortPath),
                                paint = Paint().apply {
                                    this.color = containerColor
                                    this.style = PaintingStyle.Fill
                                    this.strokeWidth = strokeWidth
                                    pathEffect = PathEffect.cornerPathEffect(1.dp.toPx())
                                }
                            )

                        }
                    }
            )

            // list
            LazyColumn(
                modifier = Modifier
                    .width(200.dp)
                    .background(containerColor, shape = RoundedCornerShape(10.dp))
            ) {
                itemsIndexed(list) { index, item ->
                    Text(
                        text = item.label,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .clickable(true, onClick = item.onClick)
                    )
                    if (index != list.size - 1) {
                        HorizontalDivider()
                    }
                }
            }
        }

    }
}

class SettingPopoverPositionProvider(
    private val x: Int = 0,
    private val y: Int = 0
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(
            anchorBounds.right - popupContentSize.width - 5.dp.value.toInt(),
            anchorBounds.bottom
        )
    }
}

data class SettingItem(
    val label: String,
    val onClick: () -> Unit
)
