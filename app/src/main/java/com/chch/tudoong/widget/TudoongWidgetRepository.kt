package com.chch.tudoong.widget

import com.chch.tudoong.data.repository.TudoongRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

// 위젯 데이터 클래스
data class TodoWidgetItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isCompleted: Boolean = false,
    val isMissed: Boolean = false
)

// 위젯용 레포지터리
@Singleton
class WidgetRepository @Inject constructor(
    private val tudoongRepository: TudoongRepository
) {
    suspend fun getTodoItems(): List<TodoWidgetItem> {
        return tudoongRepository.getTodayTodos().map { todo ->
            TodoWidgetItem(
                id = todo.id,
                text = todo.text,
                isCompleted = todo.isCompleted,
                isMissed = todo.isMissed
            )
        }
    }
}