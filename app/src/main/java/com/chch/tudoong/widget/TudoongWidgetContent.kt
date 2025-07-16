package com.chch.tudoong.widget

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.enabled
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.chch.tudoong.R

@SuppressLint("RestrictedApi")
@Composable
fun TudoongWidgetContent(
    todos: List<TodoWidgetItem>,
    todayDate: String,
) {

    Column(
        modifier = GlanceModifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = todayDate,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(start = 15.dp, top = 5.dp, bottom = 5.dp)
        )
        LazyColumn(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.White)
                .padding(10.dp)
        ) {

            items(todos) { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val bgRes = if (item.isMissed) R.drawable.widget_checkbox_border_missed
                    else if (item.isCompleted) R.drawable.widget_checkbox_border_complete
                    else R.drawable.widget_checkbox_border
                    Box(
                        modifier = GlanceModifier
                            .enabled(item.isMissed)
                            .background(ImageProvider(bgRes))
                            .size(18.dp)
                    ) {
                        val imageRes = if (item.isMissed) R.drawable.ic_remove_24
                        else if (item.isCompleted) R.drawable.ic_check_24
                        else null
                        imageRes?.let {
                            Image(
                                provider = ImageProvider(imageRes),
                                contentDescription = "checkbox icon"
                            )
                        }
                    }

                    Spacer(GlanceModifier.width(3.dp))


                    Text(
                        text = item.text,
                        maxLines = 1,
                        style = TextStyle(
                            color = ColorProvider(if (item.isMissed) R.color.on_surface_variant_alpha60 else R.color.primary)
                        )
                    )
                }
            }

            if (todos.isEmpty()) {
                item {
                    Text(
                        text = "There are no tasks for today.",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = ColorProvider(R.color.gray)
                        ),
                        modifier = GlanceModifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }


}