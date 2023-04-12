package com.example.a4.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import com.example.a4.R
import com.example.a4.models.Course
import androidx.navigation.NavController
import com.example.a4.viewmodels.Courselist
import androidx.recyclerview.widget.RecyclerView


class CourseViewHolder(private val view : View, private val nav : NavController) : RecyclerView.ViewHolder(view){
    fun create(course : Course){
        view.findViewById<TextView>(R.id.course_code).text = course.code
        view.findViewById<TextView>(R.id.course_marks).text = course.marks()
        view.findViewById<TextView>(R.id.course_term).text = course.term
        view.findViewById<TextView>(R.id.course_name).text = course.name
        view.findViewById<TextView>(R.id.course_view).setBackgroundColor(course.color())
        view.findViewById<ImageButton>(R.id.delete_button).apply{
            setOnClickListener{ course.delete() }
        }
        view.findViewById<ImageButton>(R.id.edit_button).apply{
            setOnClickListener{
                val bundle = Bundle().apply{
                    putString("code", course.code)
                }
                nav.navigate(R.id.action_courselistView_to_courseEdit, bundle)
            }
        }
    }
}

class CourseView(val vm : Courselist, private val context : Context, private val nav : NavController) : RecyclerView.Adapter<CourseViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_view, parent, false)
        return CourseViewHolder(view, nav)
    }

    override fun getItemCount(
    ): Int {
        if (vm.list.value == null){
            return 0
        }
        return vm.list.value!!.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        if (vm.list.value != null) {
            holder.create(vm.list.value!![position])
        }
    }

}