package com.chch.tudoong.presentation.ui.main

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chch.tudoong.R
import com.chch.tudoong.data.local.database.entities.DailyItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesBottomSheet(
    list: List<DailyItem> = listOf(),
    delete: (DailyItem) -> Unit,
    dismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

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

            if (list.isEmpty()) {
                Text(
                    stringResource(R.string.add_routines_hint),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(12.dp)
                )
            } else {
                Text(
                    stringResource(R.string.routines),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                Text(
                    stringResource(R.string.routines_description),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            )
            {
                itemsIndexed(list) { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.text)
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                delete.invoke(item)
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete daily item",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}