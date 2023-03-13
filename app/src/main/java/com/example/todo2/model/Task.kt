package com.example.todo2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo
    var taskName: String,
    @ColumnInfo
    var taskDescription: String,
    @ColumnInfo
    var status: Boolean
)