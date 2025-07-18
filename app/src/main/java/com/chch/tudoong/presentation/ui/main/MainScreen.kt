package com.chch.tudoong.presentation.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import com.chch.mycompose.ui.screen.checklist.CheckableRow
import com.chch.tudoong.R
import com.chch.tudoong.data.local.database.entities.TodoItem
import com.chch.tudoong.presentation.ui.component.AnimatedModeButton
import com.chch.tudoong.presentation.ui.component.ResetTimeDialog
import com.chch.tudoong.presentation.ui.component.SettingItem
import com.chch.tudoong.presentation.ui.component.SettingsPopover
import com.chch.tudoong.presentation.ui.component.TdCheckboxState
import com.chch.tudoong.presentation.viewmodel.TudoongViewModel
import com.chch.tudoong.utils.DateUtils
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: TudoongViewModel
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var showInput by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    var editMode by remember { mutableStateOf(EditMode.VIEW) }
    var editUUID by remember { mutableStateOf<String?>(null) }

    val displayDate = DateUtils.formatDateWithDayOfWeek(uiState.metadata.todayDate)

    var showDailyBottomSheet by remember { mutableStateOf(false) }
    var showYesterdayBottomSheet by remember { mutableStateOf(false) }

    var showSettingsPopover by remember { mutableStateOf(false) }
    var showResetTimeSettingDlg by remember { mutableStateOf(false) }

    var timePickerState by remember { mutableStateOf<TimePickerState?>(null) }

    val snackBarHostState = remember { SnackbarHostState() }

    var snackBarMsg by remember { mutableStateOf("") }

    LaunchedEffect(snackBarMsg) {
        if (snackBarMsg.isNotBlank()) {
            val result = snackBarHostState.showSnackbar(snackBarMsg)
            if (result == SnackbarResult.Dismissed) {
                snackBarMsg = ""
            }
        }
    }

    LaunchedEffect(uiState.metadata.resetHour, uiState.metadata.resetMin) {
        timePickerState = TimePickerState(
            initialHour = uiState.metadata.resetHour,
            initialMinute = uiState.metadata.resetMin,
            is24Hour = true
        )
    }

    fun resetInputState() {
        editMode = EditMode.VIEW
        showInput = false
        inputText = ""
        editUUID = null
    }

    fun handleChecklistInput() {
        if (inputText.isNotBlank()) {

            when (editMode) {
                EditMode.ADD -> {
                    if (!uiState.todayTodos.contain(inputText))
                        viewModel.addTodoItem(inputText)
                    else
                        snackBarMsg = context.getString(R.string.todo_already_exist)
                }

                EditMode.EDIT -> {
                    editUUID?.let {
                        viewModel.updateTodoItem(
                            uiState.todayTodos.first { it.id == editUUID }.copy(text = inputText)
                        )
                    }
                }

                else -> { /* Do Nothing */
                }
            }

        } else {
            snackBarMsg =
                if (editMode == EditMode.ADD) context.getString(R.string.empty_field_not_added)
                else context.getString(R.string.empty_field_not_saved)
        }
        resetInputState()
        editMode = EditMode.VIEW
    }

    fun isDailyItem(text: String): Boolean = uiState.dailyItems.any { it.text == text }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            MediumTopAppBar(
                title = {
                    Column() {
                        Row {
                            Text(
                                displayDate,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Reset Time: ${uiState.metadata.resetHour}:${
                                    String.format(
                                        Locale.US,
                                        "%02d",
                                        uiState.metadata.resetMin
                                    )
                                }",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .padding(bottom = 3.dp, start = 2.dp)
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                    }
                },
                actions = {

                    Box {
                        IconButton(onClick = { showSettingsPopover = true }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "setting icon"
                            )
                        }

                        if (showSettingsPopover) {
                            SettingsPopover(
                                listOf(
                                    SettingItem(
                                        label = "Change Reset Time",
                                        onClick = {
                                            showSettingsPopover = false
                                            showResetTimeSettingDlg = true
                                        })
                                )
                            ) {
                                showSettingsPopover = false
                            }
                        }

                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar() {
                // LEFT BUTTONS
                Row(
                    modifier = Modifier.weight(2f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AnimatedModeButton(
                        isActive = editMode == EditMode.EDIT,
                        icon = rememberVectorPainter(Icons.Default.ModeEdit),
                        label = stringResource(R.string.edit),
                        contentDescription = "Edit Mode Button",
                        onClick = {
                            editMode =
                                if (editMode != EditMode.EDIT) EditMode.EDIT else EditMode.VIEW
                        },
                        modifier = Modifier.weight(1f)
                    )

                    AnimatedModeButton(
                        isActive = editMode == EditMode.DELETE,
                        icon = rememberVectorPainter(Icons.Default.Delete),
                        label = stringResource(R.string.delete),
                        contentDescription = "Delete Mode Button",
                        onClick = {
                            editMode =
                                if (editMode != EditMode.DELETE) EditMode.DELETE else EditMode.VIEW
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                FloatingActionButton(
                    onClick = {
                        if (editMode == EditMode.ADD) {
                            showInput = false
                            editMode = EditMode.VIEW
                        } else {
                            showInput = true
                            editMode = EditMode.ADD
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.Top)
                        .size(52.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        if (editMode == EditMode.ADD) Icons.Default.KeyboardArrowDown else Icons.Default.Add,
                        contentDescription = "Add an item"
                    )
                }

                // RIGHT BUTTONS
                Row(
                    modifier = Modifier.weight(2f),
                    horizontalArrangement = Arrangement.End
                ) {
                    AnimatedModeButton(
                        isActive = false,
                        icon = painterResource(id = R.drawable.ic_routines),
                        label = stringResource(R.string.routines),
                        contentDescription = "Daily list",
                        onClick = {
                            resetInputState()
                            showDailyBottomSheet = true
                        },
                        modifier = Modifier.weight(1f)
                    )

                    AnimatedModeButton(
                        isActive = false,
                        icon = rememberVectorPainter(Icons.Filled.History),
                        label = stringResource(R.string.yesterday),
                        contentDescription = "Yesterday List",
                        onClick = {
                            resetInputState()
                            showYesterdayBottomSheet = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalDivider(thickness = 4.dp, modifier = Modifier.padding(horizontal = 10.dp))
            // Tudoong List
            LazyColumn(Modifier.fillMaxWidth()) {
                val todayTodos = uiState.todayTodos
                itemsIndexed(todayTodos) { index, item ->

                    CheckableRow(
                        item = item,
                        isDailyItem = isDailyItem(item.text),
                        mode = editMode,
                        onCheckStateChange = { state ->
                            viewModel.updateTodoItem(
                                when (state) {
                                    TdCheckboxState.NONE -> item.copy(
                                        isCompleted = false,
                                        isMissed = false
                                    )

                                    TdCheckboxState.DONE -> item.copy(
                                        isCompleted = true,
                                        isMissed = false
                                    )

                                    TdCheckboxState.MISSED -> item.copy(
                                        isCompleted = false,
                                        isMissed = true
                                    )
                                }
                            )

                        },
                        onEdit = {
                            inputText = it.text
                            editUUID = it.id
                            showInput = true
                        },
                        onDelete = {
                            viewModel.deleteTodoItem(it)
                        },
                        onToggleDaily = { text ->
                            if (isDailyItem(text)) {
                                viewModel.deleteDailyItem(uiState.dailyItems.fastFirst { it.text == text })
                                snackBarMsg = context.getString(R.string.removed_from_dailylist)
                            } else {
                                viewModel.addDailyItem(text)
                                snackBarMsg = context.getString(R.string.added_to_dailylist)
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }


            // Add a Item
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
                                stringResource(R.string.input_today_task_hint),
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f)
                            )
                        },
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { handleChecklistInput() }
                        )
                    )
                    IconButton(
                        onClick = {
                            showInput = false
                            handleChecklistInput()
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

        if (showDailyBottomSheet) {
            RoutinesBottomSheet(
                list = uiState.dailyItems,
                delete = {
                    viewModel.deleteDailyItem(it)
                }
            ) {
                showDailyBottomSheet = false
            }
        }

        if (showYesterdayBottomSheet) {
            YesterdayListBottomSheet(
                list = uiState.yesterdayTodos,
                canAdd = {
                    !uiState.todayTodos.contain(it)
                },
                add = {
                    viewModel.addTodoItem(it)
                }
            ) {
                showYesterdayBottomSheet = false
            }

        }

        timePickerState?.let { tps ->
            if (showResetTimeSettingDlg) {
                ResetTimeDialog(
                    onCancel = { showResetTimeSettingDlg = false },
                    onConfirm = {
                        showResetTimeSettingDlg = false
                        viewModel.updateResetHour(tps.hour, tps.minute)
                    },
                    content = {
                        TimePicker(state = tps)
                    }
                )
            }
        }


    }
}

fun List<TodoItem>.contain(text: String): Boolean {
    return this.any { todo -> todo.text == text }
}