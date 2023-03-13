package com.example.todo2.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todo2.R
import com.example.todo2.data.TasksDAO
import com.example.todo2.model.Task
import com.example.todo2.navigation.NavRoute
import kotlin.coroutines.coroutineContext

@Composable
fun EditScreen(navController: NavHostController, dao: TasksDAO, taskId: String?){
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }
    var task: Task = Task(taskName = "", taskDescription = "", status = false)
    val name: MutableState<String>
    val description: MutableState<String>
    if (taskId != "null") {
        task = dao.getTaskById(taskId)
        name = remember { mutableStateOf(task.taskName)}
        description = remember { mutableStateOf(task.taskDescription)}
    }
    else {
        name = remember { mutableStateOf("")}
        description = remember { mutableStateOf("")}
    }

    Scaffold(
        topBar = {
            TopAppBar() {
                IconButton(
                    onClick = {
                        navController.navigate(NavRoute.MainScreen.route)
                    }) {
                    Icon(painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Back" )
                }
                Spacer(Modifier.weight(1f, true))
                IconButton(
                    onClick = {
                        if (name.value == ""){
                            openDialog.value = true
                            return@IconButton
                        }
                        task.taskName = name.value
                        if (description.value != "")
                            task.taskDescription = description.value
                        else task.taskDescription = "\"\""
                        navController.navigate(NavRoute.MainScreen.route)
                        if (taskId != "null")
                            dao.update(task)
                        else
                            dao.insert(task)
                    }) {
                    Icon(painterResource(R.drawable.baseline_save_24), contentDescription = "Save" )
                }
            }
        },
        content = {padding ->
            Column(
                modifier = Modifier
                    .verticalScroll(ScrollState(0))
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)

            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = {name.value = it},
                    singleLine = true,
                    textStyle = TextStyle(fontSize =  24.sp),
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)

                )
                OutlinedTextField(
                    value = description.value,
                    onValueChange = {description.value = it},
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    )
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(
                text = "Error",
                fontSize = 24.sp
            ) },
            text = { Text(
                text = "Task name can not be empty",
                fontSize = 16.sp
            ) },
            buttons = {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { openDialog.value = false },
                    ) {
                        Text("OK", fontSize = 16.sp)
                    }
                }
            }
        )
    }
}