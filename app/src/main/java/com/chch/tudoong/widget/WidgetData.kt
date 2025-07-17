package com.chch.tudoong.widget

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.util.UUID

// 위젯 데이터 클래스
@Serializable
data class TodoWidgetItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isCompleted: Boolean = false,
    val isMissed: Boolean = false,
)

// 위젯 메타데이터
data class WidgetMetadata(
    val id: Int = 1,
    val resetHour: Int = 7,
    val resetMin: Int = 0,
    val lastResetData: String = "",
    val todayDate: String = "",
)


// 직렬화: List<TodoWidgetItem> → String
fun serializeTodos(list: List<TodoWidgetItem>): String =
    Json.encodeToString(
        ListSerializer(TodoWidgetItem.serializer()),
        list
    )

// 역직렬화: String → List<TodoWidgetItem>
fun deserializeTodos(json: String): List<TodoWidgetItem> =
    Json.decodeFromString(
        ListSerializer(TodoWidgetItem.serializer()),
        json
    )