package com.chch.tudoong.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.chch.tudoong.utils.DateUtils
import dagger.hilt.android.EntryPointAccessors

class TudoongAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val repository = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        ).widgetRepository()

        val todos = try {
            repository.getTodoItems()
        } catch (e: Exception) {
            emptyList<TodoWidgetItem>()
        }

        val metadata = try {
            repository.getMetadata()
        } catch (e: Exception) {
            null
        }

        val todayDate = if (metadata != null) DateUtils.formatDateWithDayOfWeek(metadata.todayDate)
                        else ""
        provideContent {
            TudoongWidgetContent(todos, todayDate)

        }
    }
}