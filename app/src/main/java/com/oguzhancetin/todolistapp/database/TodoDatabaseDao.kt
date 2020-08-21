package com.oguzhancetin.todolistapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun inserTodo(todo: Todo)


    @Delete
    suspend fun deleteTodo(todo: Todo)

    //order to striked todo put end of list
    @Query("SELECT * FROM todo_table ORDER BY isStrike =:isStrike ")
    fun getAllTodo(isStrike: Boolean = false): LiveData<List<Todo>>


    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodo()

    @Query("UPDATE todo_table SET isStrike=:strike WHERE todo_id=:todoId")
    suspend fun updateTodo(strike: Boolean,todoId:Long)

    @Query("DELETE FROM todo_table WHERE isStrike =:strike")
    suspend fun deleteTodosStriked(strike:Boolean = true)



}