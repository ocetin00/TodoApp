package com.oguzhancetin.todolistapp.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oguzhancetin.todolistapp.R
import com.oguzhancetin.todolistapp.database.Todo
import com.oguzhancetin.todolistapp.databinding.TodoItemBinding

// get two clickListener
// 1. for strike-through
// 2. for delete item via imageView clicked

class TodoListAdapter(
    val clickListenerCheck:TodoClickListenerCheckBox,val clickListenerImage: TodoClickListenerImageDelete):
    ListAdapter<Todo,TodoListAdapter.TodoItemViewHolder>(TodoDiffUtil()) ,Filterable {

    // hold currentData
    var mList: List<Todo>? = null

    //hold filtreddata for searchview result
    var mFilteredList: List<Todo>? = mList

    //an holder class to hold every ItemView in recycleview
    class TodoItemViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            //get parent to inflate TodoItemViewHolder
            fun from(parent: ViewGroup): TodoItemViewHolder {
                val binding: TodoItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.todo_item,
                    parent,
                    false
                )
                return TodoItemViewHolder(binding)
            }
        }

    }

    //when data change update current data
    fun updateList(lstUser: List<Todo>) {
        mList = lstUser
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        return TodoItemViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bind(position, clickListenerCheck, clickListenerImage)
    }

    private fun TodoItemViewHolder.bind(
        position: Int,
        clickListenerCheck: TodoClickListenerCheckBox,
        clickListenerImage: TodoClickListenerImageDelete
    ) {
        binding.todo = getItem(position)
        binding.clickListenerCheck = clickListenerCheck
        binding.clickListenerImage = clickListenerImage
        binding.executePendingBindings()


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val string = p0.toString()
                if (string.isEmpty()) {
                    mFilteredList = mList!!
                } else {
                    val filtredList = mList?.filter { it.todo?.toLowerCase()?.contains(string)!! }
                        ?.toMutableList()
                    mFilteredList = filtredList!!
                }

                val result = Filter.FilterResults()
                result.values = mFilteredList
                return result
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                submitList(p1!!.values as List<Todo>)
                notifyDataSetChanged()
            }

        }

    }


}

// it's setted TodoListFragment when adapter declare
class TodoClickListenerCheckBox(val clickListener:(todo: Todo,view: View)->Unit){
    fun onClickedCheckbox(todo: Todo,view: View){
        clickListener(todo,view)
    }


}
// it's setted TodoListFragment when adapter declare
class TodoClickListenerImageDelete(val clickListener:(todo: Todo,view: View)->Unit){

    fun onClickedDeleteImage(todo: Todo,view: View){
        clickListener(todo,view)
    }

}

class TodoDiffUtil() : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.todo_id == newItem.todo_id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return  oldItem == newItem
    }

}
