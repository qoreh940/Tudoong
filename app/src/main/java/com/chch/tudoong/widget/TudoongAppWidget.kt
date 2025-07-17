package com.chch.tudoong.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.chch.tudoong.utils.DateUtils
import dagger.hilt.android.EntryPointAccessors

// Preference keys
object PrefsKeys {
    val TODO_ITEMS_KEY = stringPreferencesKey("todoItems")
}

class TudoongAppWidget : GlanceAppWidget() {

    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val repository = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        ).widgetRepository()

        // Load data
        val todos = runCatching { repository.getTodoItems() }.getOrDefault(emptyList())
        val metadata = runCatching { repository.getMetadata() }.getOrNull()
        val todayDate = metadata?.let { DateUtils.formatDateWithDayOfWeek(it.todayDate) } ?: ""

        provideContent {
            val prefs = currentState<Preferences>()
            val todosString = prefs[PrefsKeys.TODO_ITEMS_KEY]
            val todolist = todosString?.let { deserializeTodos(it) } ?: todos
            TudoongWidgetContent(todolist, todayDate)

        }
    }
}