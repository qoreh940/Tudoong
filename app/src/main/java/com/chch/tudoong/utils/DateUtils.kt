package com.chch.tudoong.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    fun formatDateToShort(dateString: String): String {
        return try {
            if (dateString.isEmpty()) return ""

            val parts = dateString.split("-")
            if (parts.size == 3) {
                val month = parts[1].toInt()
                val day = parts[2].toInt()
                "$month/$day"
            } else {
                dateString
            }
        } catch (e: Exception) {
            dateString
        }
    }

    private fun formatDateInternal(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "SUN"
            Calendar.MONDAY -> "MON"
            Calendar.TUESDAY -> "TUE"
            Calendar.WEDNESDAY -> "WED"
            Calendar.THURSDAY -> "THU"
            Calendar.FRIDAY -> "FRI"
            Calendar.SATURDAY -> "SAT"
            else -> ""
        }

        return "$month/$day ($dayOfWeek)"
    }

    // String 버전
    fun formatDateWithDayOfWeek(dateString: String): String {
        return try {
            if (dateString.isEmpty()) return ""

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(dateString) ?: return dateString

            formatDateInternal(date)
        } catch (e: Exception) {
            dateString
        }
    }

    // Date 객체 버전
    fun formatDateWithDayOfWeek(date: Date?): String {
        return try {
            if (date == null) return ""
            formatDateInternal(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun getYesterdayDate(today: String): String? {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val todayDate = dateFormat.parse(today)

            val calendar = Calendar.getInstance()
            calendar.time = todayDate
            calendar.add(Calendar.DAY_OF_MONTH, -1)

            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            null
        }
    }
}