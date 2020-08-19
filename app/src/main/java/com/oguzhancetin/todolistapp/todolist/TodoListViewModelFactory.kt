package com.oguzhancetin.todolistapp.todolist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oguzhancetin.todolistapp.database.TodoDatabaseDao
import java.lang.IllegalArgumentException

class TodoListViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoListViewModel::class.java)){
            return TodoListViewModel(application) as T
        }
        throw IllegalArgumentException("invalid class")
    }
}