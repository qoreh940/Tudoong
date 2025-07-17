package com.chch.tudoong.widget

import com.chch.tudoong.data.repository.TudoongRepository
import javax.inject.Inject
import javax.inject.Singleton

// 위젯용 레포지터리
@Singleton
class WidgetRepository @Inject constructor(
    private val tudoongRepository: TudoongRepository
) {
    suspend fun getTodoItems(): List<TodoWidgetItem> {
        val list = tudoongRepository.getTodayTodos().map { todo ->
            TodoWidgetItem(
                id = todo.id,
                text = todo.text,
                isCompleted = todo.isCompleted,
                isMissed = todo.isMissed
            )
        }
        return list
    }

    suspend fun getMetadata() : WidgetMetadata {
        return tudoongRepository.getMetadata().let {
            WidgetMetadata(
                resetHour = it.resetHour,
                resetMin = it.resetMin,
                lastResetData = it.lastResetDate,
                todayDate = it.todayDate
            )
        }
    }
}