package com.chch.tudoong.data.local.database.entities

import androidx.room.*
import com.chch.tudoong.domain.model.TodoType
import com.chch.tudoong.presentation.ui.component.TdCheckboxState
import java.util.*

@Entity(tableName = "todo_items")
data class TodoItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isCompleted: Boolean = false,
    val isMissed: Boolean = false, // database version 2 에 추가됨.
    val type: TodoType, // TODAY, YESTERDAY
    val createdAt: Long = System.currentTimeMillis()
){
    val checkboxState: TdCheckboxState
        get() = when {
            isMissed -> TdCheckboxState.MISSED
            isCompleted -> TdCheckboxState.DONE
            else -> TdCheckboxState.NONE
        }
}