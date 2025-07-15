package com.chch.tudoong.widget

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.chch.tudoong.R

@SuppressLint("RestrictedApi")
@Composable
fun TudoongWidgetContent(
    todos : List<TodoWidgetItem>,
    todayDate: String
){
    LazyColumn(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        item {
            Text(
                text = todayDate,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = GlanceModifier.padding(bottom = 8.dp)
            )
        }

        items(todos) {item ->
            Text(
                text = item.text
            )
        }

        if(todos.isEmpty()){
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