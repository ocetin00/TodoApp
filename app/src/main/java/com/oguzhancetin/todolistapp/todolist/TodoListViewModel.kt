package com.oguzhancetin.todolistapp.todolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.oguzhancetin.todolistapp.database.Todo
import com.oguzhancetin.todolistapp.database.TodoDatabase
import com.oguzhancetin.todolistapp.database.TodoDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListViewModel(application: Application) : AndroidViewModel(application) {

    private  var dao: TodoDatabaseDao

    private var _alltodoData : MutableLiveData<List<Todo>>? = null
    var allTodoData: LiveData<List<Todo>>


    init {
       dao = TodoDatabase.getInstance(application,viewModelScope).todoDao()
        Log.e("viewmodel","created")
        allTodoData = dao.getAllTodo()
    }


    fun insertTodo(todo: Todo){

        viewModelScope.launch(Dispatchers.IO) {
            dao.inserTodo(todo)
        }
    }
    fun deleteAllTodo(){
        viewModelScope.launch (Dispatchers.IO){ dao.deleteAllTodo() }
    }

    fun deleteTodoWithId(todo: Todo){
        viewModelScope.launch (Dispatchers.IO){ dao.deleteTodo(todo) }
    }

    fun updateTodo(todoId:Long,strike:Boolean){
        viewModelScope.launch (Dispatchers.IO){dao.updateTodo(strike,todoId) }
    }
    fun deleteTodowithStriked(){
        viewModelScope.launch (Dispatchers.IO){dao.deleteTodosStriked()}
    }



}