package com.chch.tudoong.data.local.database.entities

import androidx.room.*
import com.chch.tudoong.domain.model.TodoType
import java.util.*

@Entity(tableName = "todo_items")
data class TodoItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isCompleted: Boolean = false,
    val type: TodoType, // TODAY, YESTERDAY
    val createdAt: Long = System.currentTimeMillis()
)