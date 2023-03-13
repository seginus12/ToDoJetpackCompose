package com.example.todo2.data.repository

import com.example.todo2.data.TasksDAO
import com.example.todo2.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val tasksDAO: TasksDAO){
    fun getAllFlow(): List<Task> = tasksDAO.getAll()
    fun insert(task: Task) = tasksDAO.insert(task)
    fun update(task: Task) = tasksDAO.update(task)
    fun delete(task: Task) = tasksDAO.delete(task)
}
