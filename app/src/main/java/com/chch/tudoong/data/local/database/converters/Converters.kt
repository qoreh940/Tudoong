package com.chch.tudoong.data.local.database.converters

import androidx.room.TypeConverter
import com.chch.tudoong.domain.model.TodoType

class Converters {
    @TypeConverter
    fun fromTodoType(type: TodoType): String = type.name

    @TypeConverter
    fun toTodoType(type: String): TodoType = TodoType.valueOf(type)
}
