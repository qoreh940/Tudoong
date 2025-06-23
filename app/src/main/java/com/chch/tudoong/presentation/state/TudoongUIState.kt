package com.chch.tudoong.presentation.state

import com.chch.tudoong.data.local.database.entities.AppMetadata
import com.chch.tudoong.data.local.database.entities.DailyItem
import com.chch.tudoong.data.local.database.entities.TodoItem

data class TudoongUiState(
    val todayTodos: List<TodoItem> = emptyList(),
    val yesterdayTodos: List<TodoItem> = emptyList(),
    val dailyItems: List<DailyItem> = emptyList(),
    val metadata: AppMetadata = AppMetadata(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)