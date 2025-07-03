package com.chch.tudoong.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chch.tudoong.data.local.database.entities.AppMetadata

@Dao
interface MetadataDao {
    @Query("SELECT * FROM app_metadata WHERE id = 1")
    suspend fun getMetadata(): AppMetadata?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMetadata(metadata: AppMetadata)

    @Query("UPDATE app_metadata SET resetHour = :hour, resetMin = :min WHERE id = 1")
    suspend fun updateResetHour(hour: Int, min: Int)

    @Query("UPDATE app_metadata SET lastResetDate = :date WHERE id = 1")
    suspend fun updateLastResetDate(date: String)

    @Query("UPDATE app_metadata SET todayDate = :date WHERE id = 1")
    suspend fun updateTodayDate(date: String)
}