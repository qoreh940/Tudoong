package com.chch.tudoong.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_metadata")
data class AppMetadata(
    @PrimaryKey val id: Int = 1, // 단일 설정 레코드
    val resetHour: Int = 7, // 리셋 시간 (24시간 형식)
    val lastResetDate: String = "", // "yyyy-MM-dd" 형식
    val todayDate: String = "" // "yyyy-MM-dd" 형식
)