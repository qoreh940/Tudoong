package com.chch.tudoong.data.repository

import com.chch.tudoong.data.local.database.dao.DailyDao
import com.chch.tudoong.data.local.database.dao.MetadataDao
import com.chch.tudoong.data.local.database.dao.TodoDao
import com.chch.tudoong.data.local.database.entities.AppMetadata
import com.chch.tudoong.data.local.database.entities.DailyItem
import com.chch.tudoong.data.local.database.entities.TodoItem
import com.chch.tudoong.domain.model.TodoType
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
    private val metadataDao: MetadataDao
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
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        todoDao.updateTodo(todoItem)
    }

    suspend fun deleteTodoItem(todoItem: TodoItem) {
        todoDao.deleteTodo(todoItem)
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
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        // 리셋이 필요한지 확인
        val needsReset = when {
            metadata.lastResetDate != today && currentHour >= metadata.resetHour -> true // 날짜 바뀜 & 리셋 시간 지남
            else -> false
        }

        if (needsReset) {
            performReset(today)
        }

    }

    private suspend fun performReset(today: String) {

        val metadata = metadataDao.getMetadata() ?: AppMetadata()
        val yesterday = getYesterdayDate(today) // 어제 날짜를 계산하는 함수

        if (metadata.todayDate == yesterday) {
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
    }

    private fun getYesterdayDate(today: String): String? {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val todayDate = dateFormat.parse(today)

            val calendar = Calendar.getInstance()
            calendar.time = todayDate
            calendar.add(Calendar.DAY_OF_MONTH, -1)

            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            null
        }
    }
}