package com.oguzhancetin.todolistapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    val todo_id: Long = 0L,
    @ColumnInfo(name = "todo")
    var todo: String,
    @ColumnInfo(name = "isStrike")
    var isStrike: Boolean =  false

)