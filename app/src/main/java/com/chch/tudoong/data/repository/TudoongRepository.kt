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
class TodoRepository @Inject constructor(
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

    suspend fun updateResetHour(hour: Int) {
        metadataDao.updateResetHour(hour)
    }

    suspend fun checkAndResetIfNeeded() {
        val metadata = getMetadata()
        val today = dateFormat.format(Date())
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        // 리셋이 필요한지 확인
        val needsReset = when {
            metadata.todayDate != today -> true // 날짜가 바뀜
            metadata.lastResetDate != today && currentHour >= metadata.resetHour -> true // 리셋 시간 지남
            else -> false
        }

        if (needsReset) {
            performReset(today)
        }
    }

    private suspend fun performReset(today: String) {
        // 1. 오늘의 할일을 어제로 이동
        todoDao.moveTodayToYesterday()

        // 2. 데일리 아이템들을 오늘의 할일로 추가
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

        // 4. 메타데이터 업데이트
        metadataDao.updateLastResetDate(today)
        metadataDao.updateTodayDate(today)
    }
}