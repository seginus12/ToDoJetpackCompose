package com.example.todo2.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.todo2.R
import com.example.todo2.data.AppDatabase
import com.example.todo2.data.TasksDAO
import com.example.todo2.navigation.NavRoute
import com.example.todo2.navigation.ToDoNavHost
import com.example.todo2.ui.theme.ToDo2Theme
import com.example.todo2.model.Task
import kotlinx.coroutines.flow.toList

@Composable
fun MainScreen(navController: NavHostController, dao: TasksDAO) {


    var expanded = remember { mutableStateOf(false) }
    var tasksStatus = remember { mutableStateOf(2) }

    var taskList: MutableList<Task>

    if (tasksStatus.value == 2)
        taskList = dao.getAll().toMutableList()
    else {
        val taskStatusBool: Boolean = tasksStatus.value == 1
        taskList = dao.getTasksByStatus(taskStatusBool).toMutableList()
    }

    Scaffold(
        topBar = {
            TopAppBar() {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = "ToDO",
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f, true))
                IconButton(
                    onClick = {expanded.value = true}) {
                    Icon(painterResource(R.drawable.baseline_filter_list_24), contentDescription = "Filter" )
                }
                Box (
                    contentAlignment = Alignment.TopEnd
                        ){
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        offset = DpOffset(x = (0).dp, y = (28).dp)
                    ) {
                        Text(
                            "All",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp).clickable(onClick = {tasksStatus.value = 2})
                        )
                        Text(
                            "Current",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp).clickable(onClick = {tasksStatus.value = 0})
                        )
                        Text(
                            "Completed",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp).clickable(onClick = {tasksStatus.value = 1})
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavRoute.EditScreen.route.plus("/null")) },
                shape = RectangleShape,
                backgroundColor = Color.DarkGray
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add new task",
                    tint = Color.White
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0))
                    .padding(start = 24.dp, end = 24.dp, bottom = 72.dp)
            ) {
                for (i in 0 until taskList.size) {
                    task(navController, dao, taskList[i])
                }
            }
        }
    )
}

//@Preview(showBackground = true)
@SuppressLint("UnrememberedMutableState")
@Composable
fun task(navController: NavHostController, dao: TasksDAO, taskEnt: Task) {
    val isChecked = mutableStateOf(taskEnt.status)

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .shadow(5.dp)
            .background(Color.Black)
            .padding(1.dp)
            .background(Color(0xFFDDDADA))
            .padding(2.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Checkbox(
            modifier = Modifier,
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = isChecked.value.not()
                taskEnt.status = isChecked.value
                dao.update(taskEnt)
            }
        )
        Column(
            modifier = Modifier
                .clickable(
                    onClick = {
                        navController.navigate(NavRoute.EditScreen.route.plus("/${taskEnt.id}"))
                    })
                .weight(8f),
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = taskEnt.taskName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = if (isChecked.value) TextDecoration.LineThrough else TextDecoration.None
            )
            Text(
                text = taskEnt.taskDescription,
                fontSize = 12.sp,
                textDecoration = TextDecoration.None,
                color = Color(0xFF5F5C5C),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = {
                dao.delete(taskEnt)
                navController.navigate(NavRoute.MainScreen.route)
            }
            ) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete selected", tint = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview () {
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val dao = db.taskDao()
    ToDo2Theme {
       MainScreen(navController = rememberNavController(), dao)
    }
}