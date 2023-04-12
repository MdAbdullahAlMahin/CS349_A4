package com.example.a4.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a4.R
import com.example.a4.models.Course
import com.example.a4.models.termOps
import com.example.a4.viewmodels.Courselist

class CourseAdd() : Fragment() {
    private lateinit var view : View
    private lateinit var vm : Courselist
    private var code = ""
    private var name = ""
    private var wd = false
    private var mark = "0"
    private var term = ""
    private lateinit var nav: NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.course_add, container, false)
        vm = ViewModelProvider(requireActivity())[Courselist::class.java]

        nav = findNavController()

        view.findViewById<EditText>(R.id.add_code).addTextChangedListener(
            object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    code = s.toString()
                }
                override fun afterTextChanged(s: Editable?) {} })

        view.findViewById<EditText>(R.id.add_name).addTextChangedListener(
            object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    name = s.toString()
                }
                override fun afterTextChanged(s: Editable?) {} })

        view.findViewById<SwitchCompat>(R.id.wd_switch).setOnCheckedChangeListener { _, isChecked ->
            wd = isChecked
            view.findViewById<EditText>(R.id.add_mark).isEnabled = !wd
            if (!wd){
                view.findViewById<EditText>(R.id.add_mark).setText(mark)
            }
        }

        view.findViewById<EditText>(R.id.add_mark).addTextChangedListener(
            object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    mark = s.toString()
                }
                override fun afterTextChanged(s: Editable?) {} })

        val termOps = termOps.toList()
        var adapter = ArrayAdapter<String>(requireContext(), R.layout.course_add, termOps)
        view.findViewById<Spinner>(R.id.add_term).adapter = adapter
        view.findViewById<Spinner>(R.id.add_term).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                term = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        view.findViewById<Button>(R.id.cancel_button).setOnClickListener{
            nav.navigate(R.id.action_courseAdd_to_courselistView)
        }

        view.findViewById<Button>(R.id.create_button).setOnClickListener{
            if (validate()){
                var course : Course
                if (wd){
                    course = Course(code, name, term, -1, vm)
                }
                else {
                    course = Course(code,name,term,mark.toInt(),vm)
                }
                vm.addCourse(course)
                nav.navigate(R.id.action_courseAdd_to_courselistView)
            }
        }

        return view
    }

    private fun validate() : Boolean{
        val mark_check = mark.toIntOrNull() != null || wd
        return code !== "" && name!== "" && mark_check && term != ""
    }
}