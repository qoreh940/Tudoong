package com.chch.tudoong.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chch.tudoong.ui.component.AnimatedModeButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){

    var showInput by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }

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
                    isActive = false,
                    icon = Icons.Default.Delete,
                    contentDescription = "Delete Mode Button",
                    onClick = {}
                )

                Spacer(Modifier.weight(1f))
                FloatingActionButton(
                    onClick = {
                        showInput = true
                    },
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
            // Add a CheckItem
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = showInput,
                enter = slideInVertically(initialOffsetY = { +it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { +it }) + fadeOut()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(color = MaterialTheme.colorScheme.surfaceContainer),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = inputText,
                        placeholder = {
                            Text(
                                "Please enter a checklist item",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f)
                            )
                        },
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { /*handleChecklistInput()*/ }
                        )
                    )
                    IconButton(
                        onClick = {
                            showInput = false
                        /*handleChecklistInput()*/
                        },
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = "Done",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

            }
        }

    }
}