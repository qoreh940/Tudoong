package com.chch.tudoong.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chch.tudoong.data.local.database.entities.DailyItem

@Dao
interface DailyDao {
    @Query("SELECT * FROM daily_items ORDER BY createdAt ASC")
    suspend fun getAllDailyItems(): List<DailyItem>

    @Insert
    suspend fun insertDailyItem(dailyItem: DailyItem)

    @Update
    suspend fun updateDailyItem(dailyItem: DailyItem)

    @Delete
    suspend fun deleteDailyItem(dailyItem: DailyItem)
}