package com.chch.tudoong.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chch.tudoong.data.local.database.converters.Converters
import com.chch.tudoong.data.local.database.dao.DailyDao
import com.chch.tudoong.data.local.database.dao.MetadataDao
import com.chch.tudoong.data.local.database.dao.TodoDao
import com.chch.tudoong.data.local.database.entities.AppMetadata
import com.chch.tudoong.data.local.database.entities.DailyItem
import com.chch.tudoong.data.local.database.entities.TodoItem

@Database(
    entities = [TodoItem::class, DailyItem::class, AppMetadata::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TudoongDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun dailyDao(): DailyDao
    abstract fun metadataDao(): MetadataDao
}