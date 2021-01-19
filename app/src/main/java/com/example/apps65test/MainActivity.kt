package com.example.apps65test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.apps65test.fragments.EmployeesListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.employees_list_fragment.*


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employees_main)

        nav = Navigation.findNavController(this, R.id.fragment_nav)
    }

    fun onEmployeeClick(view: View) {
        val id = view.tag as Int
        val direction =
            EmployeesListFragmentDirections.actionEmployeesListFragmentToEmployeeCardFragment2(id)
        findNavController(employees_list_fragment.id).navigate(direction)
    }

}