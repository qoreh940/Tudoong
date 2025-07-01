package com.chch.tudoong.presentation.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chch.tudoong.data.local.database.entities.DailyItem
import com.chch.tudoong.data.local.database.entities.TodoItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YesterdayListBottomSheet(
    list: List<TodoItem> = listOf(),
    add: (String) -> Unit,
    dismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    val completedList = list.filter { it.isCompleted }
    val notCompletedList = list.filter { !it.isCompleted }
    var formattedDate : String? = null
    list.firstOrNull()?.let {
        val date = Date(it.createdAt)
        val formatter = SimpleDateFormat("M월 d일", Locale.KOREAN)
        formattedDate= formatter.format(date)
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = { dismiss.invoke() }
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(20.dp))

            if(formattedDate != null){
                Text(text=formattedDate, style = MaterialTheme.typography.headlineSmall)
                Text(text="지난 TODOs", style = MaterialTheme.typography.bodyMedium)
            } else {
                Text(text = "어제는 들르지 않으셨군요! 그래도 괜찮습니다:)")
            }

            if(completedList.isNotEmpty()){
                Text(
                    "완료한 TODOs",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    itemsIndexed(completedList) { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "- " + item.text)
                            Spacer(Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    add.invoke(item.text)
                                }
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "add to today",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }


            if(notCompletedList.isNotEmpty()){
                Spacer(Modifier.height(10.dp))
                Text(
                    "완료하지 못 한 TODOs",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(notCompletedList) { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "- " + item.text)
                            Spacer(Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    add.invoke(item.text)
                                }
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "add to today",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}