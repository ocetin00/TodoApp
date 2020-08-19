package com.oguzhancetin.todolistapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Todo::class),version = 5,exportSchema = false)
abstract class TodoDatabase : RoomDatabase(){

    abstract fun todoDao():TodoDatabaseDao


    companion object{
        @Volatile
        private  var INSTANCE: TodoDatabase? = null

     fun getInstance(context: Context,scope:CoroutineScope):TodoDatabase{
        return INSTANCE ?: synchronized(this){
                val temp = Room.databaseBuilder(context.applicationContext,TodoDatabase::class.java,"todolist_database")
                    .addCallback(TodoDatabase.MyCallback(scope))
                    .fallbackToDestructiveMigration()
                     .build()
                INSTANCE = temp
                temp
                }


     }

    }

    class MyCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {dao->
                {   Log.e("todo","eklendi")
                    scope.launch {
                        val todo1 = Todo(todo = "elma al")
                        val todo2 = Todo(todo = "sebze al")

                        dao.todoDao().deleteAllTodo()
                        dao.todoDao().inserTodo(todo1)
                        dao.todoDao().inserTodo(todo2)
                    }

            }

        }

        /*private suspend fun populateDb(dao: TodoDatabaseDao){
            val todo1 = Todo(todo = "elma al")
            val todo2 = Todo(todo = "sebze al")

            dao.deleteAllTodo()
            dao.inserTodo(todo1)
            dao.inserTodo(todo2)
        }*/
    }
    }}


