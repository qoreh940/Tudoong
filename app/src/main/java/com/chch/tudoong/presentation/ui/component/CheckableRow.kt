package com.chch.mycompose.ui.screen.checklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chch.tudoong.data.local.database.entities.TodoItem
import com.chch.tudoong.presentation.ui.component.AnimatedCheckbox
import com.chch.tudoong.presentation.ui.main.EditMode


@Composable
fun CheckableRow(
    item: TodoItem,
    isDailyItem: Boolean,
    mode: EditMode = EditMode.VIEW,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: (TodoItem) -> Unit,
    onEdit: (TodoItem) -> Unit,
    onToggleDaily: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onCheckedChange(!item.isCompleted) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedCheckbox(
            checked = item.isCompleted,
            onCheckedChange = null // Row에서 제어할 때 null로 둠!
        )
        Spacer(Modifier.width(4.dp))
        AnimatedCheckItemText(
            text = item.text,
            isCompleted = item.isCompleted
        )
        Spacer(Modifier.weight(1f))
        when (mode) {
            EditMode.EDIT -> {
                IconButton(
                    onClick = { onEdit.invoke(item) }
                ) {
                    Icon(
                        Icons.Default.ModeEdit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            EditMode.DELETE -> {
                IconButton(
                    onClick = { onDelete.invoke(item) }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            EditMode.VIEW -> {
                IconButton(
                    onClick = { onToggleDaily.invoke(item.text) }
                ) {
                    Icon(
                        if (isDailyItem) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Add a daily item",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            else -> {}
        }
    }
}