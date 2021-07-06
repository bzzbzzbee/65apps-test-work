package com.example.apps65test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.apps65test.databinding.EmployeesMainBinding
import com.example.apps65test.fragments.EmployeesListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.employees_list_fragment.*

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var nav: NavController
    private lateinit var binding: EmployeesMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmployeesMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        nav = Navigation.findNavController(this, R.id.fragment_nav)
    }
//TODO Проверить есть ли еще варианты реализации навигации по клику на рабочего из списка
    fun onEmployeeClick(view: View) {
        val id = view.tag as Int
        val direction =
            EmployeesListFragmentDirections.actionEmployeesListFragmentToEmployeeCardFragment2(id)
        findNavController(employees_list_fragment.id).navigate(direction)
    }
}