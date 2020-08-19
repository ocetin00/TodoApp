package com.oguzhancetin.todolistapp

import android.graphics.Paint
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oguzhancetin.todolistapp.database.Todo



@BindingAdapter("todoText")
fun CheckBox.setText(todo:Todo){
    text= todo.todo
    if(todo.isStrike){
        this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }else{
        this.paintFlags = Paint.ANTI_ALIAS_FLAG
    }


}