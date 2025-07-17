package com.chch.tudoong.data.repository

import android.content.Context
import com.chch.tudoong.data.local.database.dao.DailyDao
import com.chch.tudoong.data.local.database.dao.MetadataDao
import com.chch.tudoong.data.local.database.dao.TodoDao
import com.chch.tudoong.data.local.database.entities.AppMetadata
import com.chch.tudoong.data.local.database.entities.DailyItem
import com.chch.tudoong.data.local.database.entities.TodoItem
import com.chch.tudoong.domain.model.TodoType
import com.chch.tudoong.utils.DateUtils
import com.chch.tudoong.widget.WidgetUpdateManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TudoongRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val dailyDao: DailyDao,
    private val metadataDao: MetadataDao,
    @ApplicationContext private val context: Context,
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    suspend fun getTodayTodos(): List<TodoItem> = todoDao.getTodayTodos()

    suspend fun getYesterdayTodos(): List<TodoItem> = todoDao.getYesterdayTodos()

    suspend fun getAllDailyItems(): List<DailyItem> = dailyDao.getAllDailyItems()

    suspend fun getMetadata(): AppMetadata {
        return metadataDao.getMetadata() ?: AppMetadata().also {
            metadataDao.insertOrUpdateMetadata(it)
        }
    }

    suspend fun addTodoItem(text: String) {
        val todoItem = TodoItem(text = text, type = TodoType.TODAY)
        todoDao.insertTodo(todoItem)
        WidgetUpdateManager.markForUpdate()
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        todoDao.updateTodo(todoItem)
        WidgetUpdateManager.markForUpdate()
    }

    suspend fun deleteTodoItem(todoItem: TodoItem) {
        todoDao.deleteTodo(todoItem)
        WidgetUpdateManager.markForUpdate()
    }

    suspend fun addDailyItem(text: String) {
        val dailyItem = DailyItem(text = text)
        dailyDao.insertDailyItem(dailyItem)
    }

    suspend fun updateDailyItem(dailyItem: DailyItem) {
        dailyDao.updateDailyItem(dailyItem)
    }

    suspend fun deleteDailyItem(dailyItem: DailyItem) {
        dailyDao.deleteDailyItem(dailyItem)
    }

    suspend fun updateResetHour(hour: Int, min: Int) {
        metadataDao.updateResetHour(hour, min)
    }

    suspend fun checkAndResetIfNeeded() {
        val metadata = getMetadata()
        val today = dateFormat.format(Date())

        val todayDate = dateFormat.parse(today)!!
        val lastResetDate = dateFormat.parse(metadata.lastResetDate)!!
        val diffDays = ((todayDate.time - lastResetDate.time) / (1000 * 60 * 60 * 24)).toInt()

        // 리셋이 필요한지 확인
        val needsReset = when {
            // 데이터 비정상, 보수적인 처리 필요
            diffDays < 0 -> true

            // 2일 이상 차이나면 무조건 리셋
            diffDays >= 2 -> true

            // 하루 차이이고, 현재 시간이 리셋 시간을 지났으면 리셋
            diffDays == 1 -> {
                val now = Calendar.getInstance()
                val currentTime = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)
                val resetTime = metadata.resetHour * 60 + metadata.resetMin
                currentTime >= resetTime
            }

            else -> false
        }

        if (needsReset) {
            performReset(today)
        }
    }

    private suspend fun performReset(today: String) {

        val metadata = metadataDao.getMetadata() ?: AppMetadata()
        val yesterday = DateUtils.getYesterdayDate(today) // 어제 날짜를 계산하는 함수

        if (metadata.todayDate == yesterday) {
            todoDao.deleteAllTodosByType(TodoType.YESTERDAY)
            todoDao.markUncompletedAsMissed()
            todoDao.moveTodayToYesterday()

        } else if (metadata.todayDate.isNotEmpty()) {
            todoDao.deleteAllTodos()
        }

        val dailyItems = dailyDao.getAllDailyItems()
        val todayTodos = dailyItems.map { dailyItem ->
            TodoItem(
                text = dailyItem.text,
                type = TodoType.TODAY,
                isCompleted = false
            )
        }
        if (todayTodos.isNotEmpty()) {
            todoDao.insertTodos(todayTodos)
        }

        // 메타데이터 업데이트
        metadataDao.updateLastResetDate(today)
        metadataDao.updateTodayDate(today)

        WidgetUpdateManager.markForUpdate()
    }
}