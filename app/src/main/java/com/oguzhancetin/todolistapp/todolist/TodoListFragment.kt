package com.oguzhancetin.todolistapp.todolist

import android.app.ActionBar
import android.app.Activity
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.graphics.Paint
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.oguzhancetin.todolistapp.R
import com.oguzhancetin.todolistapp.database.Todo
import com.oguzhancetin.todolistapp.databinding.TodoListFragmentBinding
import com.oguzhancetin.todolistapp.shareData
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoListFragment : Fragment() {




    private lateinit var viewModel: TodoListViewModel
    private lateinit var todoAdapter:TodoListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TodoListFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.todo_list_fragment,container,false)
        setHasOptionsMenu(true)


        //to set ttodo item text via onClickListener and Update
            todoAdapter = TodoListAdapter(TodoClickListenerCheckBox{todo,view->
            Log.e("id",id.toString())
            if (view.checkBox.isChecked) {
                    Log.e("checkbox", "true")
                    view.checkBox.paintFlags = view.checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    viewModel.updateTodo(todo.todo_id, true)


                } else {
                    Log.e("checkbox", "false")
                    view.checkBox.paintFlags = Paint.ANTI_ALIAS_FLAG
                    viewModel.updateTodo(todo.todo_id, false)

                }//click listener to delete item
            },TodoClickListenerImageDelete{ todo, view ->
            viewModel.deleteTodoWithId(todo)
        })

        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.todoRecyclerView.layoutManager = layoutManager
        binding.todoRecyclerView.adapter = todoAdapter

        //view model created via factory pattern
        viewModel = TodoListViewModelFactory(requireActivity().application).create(TodoListViewModel::class.java)

        //put data RececyclerView via TodoAdapter
        viewModel.allTodoData.observe(viewLifecycleOwner, Observer {todos ->
          todos?.let {

                  //List reversed to put first of list
                  todoAdapter.submitList(todos.reversed())
                  //update current list in adapter to find specific item
                  todoAdapter.updateList(todos.reversed())

          }
        })



        //add todo item in recyclerview
        binding.buttonAdd.setOnClickListener {
            Log.e("button","clicked")
            val todoText = binding.editTextTodoContent.text.toString()

            //control if it is empty
            if(!todoText.trim().isEmpty()){
                viewModel.insertTodo(Todo(todo = todoText))
                binding.editTextTodoContent.text?.clear()

                //observe dataset when add data from database move to scrol to first item
                todoAdapter.registerAdapterDataObserver(object : AdapterDataObserver(){
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        if (positionStart == 0) {
                            layoutManager.scrollToPosition(0)
                        }
                    }
                })
                //close keybord
                (requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(it.windowToken,0)
                Toast.makeText(requireContext(),"Task Added",Toast.LENGTH_SHORT).show()
            }


        }

        return binding.root
    }

    //create custom option menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        val menager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        //set SearcView
        (menu.findItem(R.id.search_action).actionView as SearchView).apply {
            setSearchableInfo(menager.getSearchableInfo(requireActivity().componentName))
            setIconifiedByDefault(false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextChange(p0: String?): Boolean {
                    todoAdapter.filter.filter(p0)
                    return true
                }

                override fun onQueryTextSubmit(p0: String?): Boolean {

                    return false
                }

            })
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete_allitems_action -> viewModel.deleteAllTodo().also { return true } //deleted all data
            R.id.delete_selecteditems_action -> viewModel.deleteTodowithStriked().also { return true }// deleted items which strike-through
            R.id.share_todolist_action -> shareData(requireContext(),viewModel.allTodoData?.value).also { return true }

            else -> return false
        }

    }



}