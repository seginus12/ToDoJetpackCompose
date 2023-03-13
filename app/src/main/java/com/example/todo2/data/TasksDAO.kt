package com.example.todo2.data

import androidx.room.*
import com.example.todo2.model.Task

@Dao
interface TasksDAO {
    @Query("SELECT * FROM tasks_table")
    fun getAll(): List<Task>

    @Update
    fun update(task: Task)

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM tasks_table WHERE id=(:taskId)")
    fun getTaskById(taskId: String?): Task

    @Query("SELECT * FROM tasks_table WHERE status=(:status)")
    fun getTasksByStatus(status: Boolean?): List<Task>
}
