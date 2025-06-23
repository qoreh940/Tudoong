package com.chch.tudoong.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chch.tudoong.data.local.database.entities.TodoItem
import com.chch.tudoong.domain.model.TodoType

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_items WHERE type = :type ORDER BY createdAt ASC")
    suspend fun getTodosByType(type: TodoType): List<TodoItem>

    @Query("SELECT * FROM todo_items WHERE type = 'TODAY' ORDER BY createdAt ASC")
    suspend fun getTodayTodos(): List<TodoItem>

    @Query("SELECT * FROM todo_items WHERE type = 'YESTERDAY' ORDER BY createdAt ASC")
    suspend fun getYesterdayTodos(): List<TodoItem>

    @Insert
    suspend fun insertTodo(todoItem: TodoItem)

    @Insert
    suspend fun insertTodos(todoItems: List<TodoItem>)

    @Update
    suspend fun updateTodo(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodo(todoItem: TodoItem)

    @Query("DELETE FROM todo_items WHERE type = :type")
    suspend fun deleteAllTodosByType(type: TodoType)

    @Query("UPDATE todo_items SET type = 'YESTERDAY' WHERE type = 'TODAY'")
    suspend fun moveTodayToYesterday()
}