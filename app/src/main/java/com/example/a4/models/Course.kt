package com.example.a4.models

import android.graphics.Color
import com.example.a4.viewmodels.Courselist

// List of options for a term dropdown
val termOps = listOf<String>("F20", "W21", "S21", "F21", "W22", "S22", "F22", "W23", "S23", "F23")

// A class that models a single course
data class Course(val code: String, var n: String, var t: String, var m: Int, val vm : Courselist) {
    val name = n
    val term = t
    var marks = m
    // Position of term in termOps list
    val termIndex = termOps.indexOf(term)
    // We use WD = -1 for simplicity
    // Returns string display of marks
    fun marks() : String{
        if (marks == -1){ return "WD" }
        return marks.toString()
    }
    // Returns color of this course on the display
    fun color() : Int {
        var hex = ""
        when (marks) {
            -1 -> {hex = "2F4F4F"}
            in 0..49 -> {hex = "F08080"}
            in 50..59 -> {hex = "ADD8E6"}
            in 60..90 -> {hex = "90EE90"}
            in 91..95 -> {hex = "C0C0C0"}
            in 96..100 -> {hex = "FFD700"}
        }
        return Color.parseColor(/* colorString = */hex)
    }
    fun delete(){
        vm.removeCourse(code)
    }
}