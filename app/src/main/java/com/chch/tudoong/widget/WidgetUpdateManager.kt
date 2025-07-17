package com.chch.tudoong.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicBoolean

object WidgetUpdateManager {

    private val needsUpdate = AtomicBoolean(false)

    fun markForUpdate() {
        needsUpdate.set(true)
    }

    suspend fun updateIfNeeded(context: Context) {
        if (needsUpdate.compareAndSet(true, false)) {
            context.updateAllWidgets()
        }
    }
}

suspend fun Context.updateAllWidgets() {

    val repository = EntryPointAccessors.fromApplication(
        this.applicationContext,
        WidgetEntryPoint::class.java
    ).widgetRepository()
    val todos = runCatching { repository.getTodoItems() }.getOrDefault(emptyList())
    GlanceAppWidgetManager(this)
        .getGlanceIds(TudoongAppWidget::class.java)
        .forEach { glanceId ->
            updateAppWidgetState(this, glanceId) { prefs ->
                prefs[PrefsKeys.TODO_ITEMS_KEY] = serializeTodos(todos)
            }.let {
                delay(500)
                TudoongAppWidget().update(this, glanceId)
            }
        }
}