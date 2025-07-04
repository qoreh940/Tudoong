package com.chch.tudoong.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chch.tudoong.data.local.database.entities.DailyItem
import com.chch.tudoong.data.local.database.entities.TodoItem
import com.chch.tudoong.data.repository.TudoongRepository
import com.chch.tudoong.presentation.state.TudoongUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "TudoongViewModel"

@HiltViewModel
class TudoongViewModel @Inject constructor(
    private val repository: TudoongRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TudoongUiState())
    val uiState: StateFlow<TudoongUiState> = _uiState.asStateFlow()

    init {
        checkResetAndLoadData()
    }

    private fun checkResetAndLoadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                repository.checkAndResetIfNeeded()
                loadAllData()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    private suspend fun loadAllData() {
        try {
            val todayTodos = repository.getTodayTodos()
            val yesterdayTodos = repository.getYesterdayTodos()
            val dailyItems = repository.getAllDailyItems()
            val metadata = repository.getMetadata()

            _uiState.value = _uiState.value.copy(
                todayTodos = todayTodos,
                yesterdayTodos = yesterdayTodos,
                dailyItems = dailyItems,
                metadata = metadata,
                isLoading = false,
                errorMessage = null
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = e.message
            )
        }
    }

    fun addTodoItem(text: String) {
        if(uiState.value.todayTodos.any{ it.text == text}) return

        viewModelScope.launch {
            try {
                repository.addTodoItem(text)
                val updatedTodos = repository.getTodayTodos()
                _uiState.update { it.copy(todayTodos = updatedTodos) }
                Log.d(TAG, "addTodoItem: ${_uiState.value.todayTodos}")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
                Log.d(TAG, "addTodoItem: exception : ${_uiState.value}")
            }
        }
    }

    fun updateTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            try {
                repository.updateTodoItem(todoItem)
                val updatedTodos = repository.getTodayTodos()
                _uiState.value = _uiState.value.copy(todayTodos = updatedTodos)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            try {
                repository.deleteTodoItem(todoItem)
                val updatedTodos = repository.getTodayTodos()
                _uiState.value = _uiState.value.copy(todayTodos = updatedTodos)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun toggleTodoComplete(todoItem: TodoItem) {
        updateTodoItem(todoItem.copy(isCompleted = !todoItem.isCompleted))
    }

    fun addDailyItem(text: String) {
        if (uiState.value.dailyItems.any { it.text == text }) return

        viewModelScope.launch {
            try {
                repository.addDailyItem(text)
                val updatedDailyItems = repository.getAllDailyItems()
                _uiState.value = _uiState.value.copy(dailyItems = updatedDailyItems)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun updateDailyItem(dailyItem: DailyItem) {
        viewModelScope.launch {
            try {
                repository.updateDailyItem(dailyItem)
                val updatedDailyItems = repository.getAllDailyItems()
                _uiState.value = _uiState.value.copy(dailyItems = updatedDailyItems)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteDailyItem(dailyItem: DailyItem) {
        viewModelScope.launch {
            try {
                repository.deleteDailyItem(dailyItem)
                val updatedDailyItems = repository.getAllDailyItems()
                _uiState.value = _uiState.value.copy(dailyItems = updatedDailyItems)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun updateResetHour(hour: Int, min: Int) {
        viewModelScope.launch {
            try {
                repository.updateResetHour(hour, min)
                val updatedMetadata = repository.getMetadata()
                _uiState.value = _uiState.value.copy(metadata = updatedMetadata)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun refreshData() {
        checkResetAndLoadData()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}