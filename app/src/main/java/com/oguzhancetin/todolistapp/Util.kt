package com.oguzhancetin.todolistapp

import android.content.Context
import android.content.Intent
import android.graphics.PointF
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.oguzhancetin.todolistapp.database.Todo


/*class LinearLayoutMenagerWithSmoothScroller(context: Context, orientation:Int= RecyclerView.VERTICAL, reverseLayout:Boolean=false): LinearLayoutManager(context,
    orientation,reverseLayout) {




    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        var smoothScroller = SmoothScroller(recyclerView!!.getContext()!!);
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);

    }
    class SmoothScroller(val context: Context) : LinearSmoothScroller(context){

        override fun getVerticalSnapPreference(): Int {
            return LinearSmoothScroller.SNAP_TO_START

        }

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return LinearLayoutMenagerWithSmoothScroller(context).computeScrollVectorForPosition(targetPosition)
        }
    }
}
fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}*/

fun shareData(context: Context ,list:List<Todo>?){
    list?.let {
        var buffer = StringBuffer()
        val content:String

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type ="text/plain"
            list.forEach {
                buffer.append("-${it.todo} \n")

            }
            content= buffer.toString()
            putExtra(Intent.EXTRA_TEXT,content)


        }
        val shareIntent = Intent.createChooser(sendIntent,buffer.toString())
        context.startActivity(shareIntent)







    }



}
