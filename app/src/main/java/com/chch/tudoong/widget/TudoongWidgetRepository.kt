package com.chch.tudoong.widget

import androidx.room.PrimaryKey
import com.chch.tudoong.data.repository.TudoongRepository
import com.chch.tudoong.utils.logd
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
data class WidgetMetadata(
    val resetHour : Int = 7,
    val resetMin : Int = 0,
    val lastResetData : String = "",
    val todayDate : String = ""
)

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