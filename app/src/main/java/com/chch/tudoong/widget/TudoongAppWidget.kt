package com.chch.tudoong.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text
import dagger.hilt.android.EntryPointAccessors

class TudoongAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.
        val repository = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        ).widgetRepository()

        provideContent {
            // create your AppWidget here
            Text("Tudoong 위젯입니다")
//            TudoongWidgetContent(repository)

        }
    }
}