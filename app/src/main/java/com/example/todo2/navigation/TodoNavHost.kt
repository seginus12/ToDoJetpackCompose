package com.example.todo2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo2.data.TasksDAO
import com.example.todo2.screens.*


sealed class NavRoute(val route: String){
    object MainScreen: NavRoute("main_screen")
    object EditScreen: NavRoute("edit_screen")
}

@Composable
fun ToDoNavHost(dao: TasksDAO){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoute.MainScreen.route)
    {
        composable(NavRoute.MainScreen.route)
        {
            MainScreen(navController, dao)
        }
        composable(
            route = NavRoute.EditScreen.route.plus("/{taskId}"),
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.StringType
                    defaultValue = "Default"
                }
            )
        )
        {
            val taskId = it.arguments?.getString("taskId")
            EditScreen(navController, dao, taskId)
        }
    }
}