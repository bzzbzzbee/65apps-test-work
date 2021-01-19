package com.example.apps65test.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps65test.R
import com.example.apps65test.adapters.EmployeesListAdapter
import com.example.apps65test.ui.EmployeesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.employees_list_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class EmployeesListFragment: Fragment() {
    private val args: EmployeesListFragmentArgs by navArgs()

    @Inject
    lateinit var employeeViewModelFactory: EmployeesViewModel.AssistedFactory

    private val employeesViewModel: EmployeesViewModel by viewModels {
        EmployeesViewModel.provideFactory(
            employeeViewModelFactory,
            args.specialitySelected
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.employees_list_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        val adapter = EmployeesListAdapter()

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this.context)

        employeesViewModel.employees.observe(viewLifecycleOwner, { employee ->
            employee?.let { adapter.submitList(it.sortedBy { it.lastName }) }
        })
    }
}