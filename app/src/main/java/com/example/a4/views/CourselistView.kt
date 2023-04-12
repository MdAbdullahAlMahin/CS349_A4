package com.example.a4.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4.R
import com.example.a4.viewmodels.Courselist
import com.example.a4.viewmodels.filterOps
import com.example.a4.viewmodels.sortOps

class CourselistView() : Fragment() {
    private lateinit var view : View
    private lateinit var vm : Courselist
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var nav: NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.courselist_view, container, false)
        vm = ViewModelProvider(requireActivity())[Courselist::class.java]

        nav = findNavController()

        val sortOps = sortOps.toList()
        val filterOps = filterOps.toList()
        var adapter = ArrayAdapter<String>(requireContext(), R.layout.courselist_view, filterOps)
        view.findViewById<Spinner>(R.id.filter_spinner).adapter = adapter
        adapter = ArrayAdapter<String>(requireContext(), R.layout.courselist_view, sortOps)
        view.findViewById<Spinner>(R.id.sort_spinner).adapter = adapter
        view.findViewById<Spinner>(R.id.filter_spinner).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position).toString()
                vm.changeFilter(item)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        view.findViewById<Spinner>(R.id.sort_spinner).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position).toString()
                vm.changeSort(item)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewManager = LinearLayoutManager(this.context)
        view.findViewById<RecyclerView>(R.id.course_view).layoutManager = viewManager
        vm.list.observe(viewLifecycleOwner, Observer { courses ->
            view.findViewById<RecyclerView>(R.id.course_view).adapter =
                this.context?.let { CourseView (vm, it, nav) }
        })

        view.findViewById<ImageButton>(R.id.add_button).apply{
            setOnClickListener {
                nav.navigate(R.id.action_courselistView_to_courseAdd)
            }
        }

        return view
    }

}
