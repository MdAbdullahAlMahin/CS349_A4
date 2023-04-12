package com.example.a4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.fragment.NavHostFragment
import com.example.a4.viewmodels.Courselist

class MainActivity : AppCompatActivity() {
    lateinit var vm : Courselist
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProvider(this)[Courselist::class.java]
    }
}