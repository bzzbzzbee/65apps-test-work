package com.example.apps65test.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps65test.adapters.EmployeesListAdapter
import com.example.apps65test.databinding.EmployeesListFragmentBinding
import com.example.apps65test.ui.EmployeesViewModel
import dagger.hilt.android.AndroidEntryPoint
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

    private var _binding: EmployeesListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EmployeesListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onStart() {
        super.onStart()

        val adapter = EmployeesListAdapter(resources)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this.context)

        employeesViewModel.employees.observe(viewLifecycleOwner, { employee ->
            employee?.let { adapter.submitList(it.sortedBy { it.lastName }) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}