package com.example.todo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todo2.data.AppDatabase
import com.example.todo2.navigation.ToDoNavHost
import com.example.todo2.ui.theme.ToDo2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(this)
        val dao = db.taskDao()
        setContent {
            ToDo2Theme {
                Surface(
                    modifier = Modifier
                        .padding(0.dp)
                ) {
                    ToDoNavHost(dao)
                }
            }
        }
    }
}

@Composable
fun editTopBar(){
    TopAppBar() {
        IconButton(
            onClick = { }) {
            Icon(painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Back" )
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(
            onClick = { }) {
            Icon(painterResource(R.drawable.baseline_filter_list_24), contentDescription = "Delete selected" )
        }
    }
}