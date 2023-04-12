package com.example.a4.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a4.models.Course

// List of options for filter dropdown
val filterOps = listOf<String>("All Courses", "CS Only", "Math Only", "Other Only")
// List of options for sort dropdown
val sortOps = listOf<String>("By Course Code", "By Term", "By Mark")

// Models a list of courses
class Courselist() : ViewModel() {
    // Unedited list
    private var listOrg = mutableListOf<Course>(Course("CS349", "User Interfaces", "F21", 75, this))
    // List to be displayed
    var list = MutableLiveData<List<Course>>(listOrg.toMutableList())
    var filter = "All Courses"
    var sort = "By Course Code"
    // Checks if a course with the given code does not exist in the list
    fun verifyCode(code : String) : Boolean{
        var check = false
        listOrg.forEach {
            if (it.code == code){ check = true}
        }
        return check
    }
    fun addCourse(course : Course){
        listOrg.add(course)
        makeList()
    }
    fun removeCourse(code : String){
        listOrg.filter{it.code != code}
        makeList()
    }
    fun editCourse(code : String, course : Course){
        listOrg.filter{it.code != code}
        listOrg.add(course)
        makeList()
    }
    fun changeFilter(n : String){
        filter = n
        makeList()
    }
    fun changeSort(n : String){
        sort = n
        makeList()
    }
    // Filters and sorts the list to make is display-worthy
    private fun makeList(){
        var temp = listOrg.toMutableList()
        when (filter){
            "All Courses" -> temp.filter{ true }
            "CS Only" -> temp.filter{ it.code.startsWith("CS") }
            "Math Only" -> temp.filter{ it.code.startsWith("MATH") || it.code.startsWith("STAT") }
            "Other Only" -> temp.filter{ !it.code.startsWith("CS") &&
                                         !it.code.startsWith("MATH") &&
                                         !it.code.startsWith("STAT") }
        }
        when (sort){
            "By Course Code" -> temp.sortBy{ it.code }
            "By Term" -> temp.sortBy { it.termIndex }
            "By Mark" -> temp.sortBy{ it.marks }
        }
        list.value = temp
    }
}