package com.chch.tudoong.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chch.tudoong.ui.component.AnimatedModeButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){

    val today = Calendar.getInstance()
    val formatter = SimpleDateFormat("M월 d일", Locale.KOREAN)
    val formattedDate = formatter.format(today.time)

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Column(Modifier.padding(horizontal = 10.dp)) {
                        Text(formattedDate,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                        Spacer(Modifier.height(10.dp))
                        HorizontalDivider(thickness = 4.dp,)
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "setting icon"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            ) {
                AnimatedModeButton(
                    isActive = false,
                    icon = Icons.Default.ModeEdit,
                    contentDescription = "Edit Mode Button",
                    onClick = {}
                )

                AnimatedModeButton(
                    isActive = true,
                    icon = Icons.Default.Delete,
                    contentDescription = "Delete Mode Button",
                    onClick = {}
                )

                Spacer(Modifier.weight(1f))
                FloatingActionButton(
                    onClick = {},
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add an item"
                    )
                }
            }
        }
    ) {innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {

        }

    }
}