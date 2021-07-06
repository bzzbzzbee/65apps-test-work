package com.example.apps65test.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apps65test.R
import com.example.apps65test.utilities.CircleTransform
import com.example.apps65test.adapters.SpecialityListAdapter
import com.example.apps65test.databinding.EmployeeCardFragmentBinding
import com.example.apps65test.ui.EmployeeCardViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EmployeeCardFragment: Fragment() {
    private val args: EmployeeCardFragmentArgs by navArgs()

    @Inject
    lateinit var employeeCardViewModelFactory: EmployeeCardViewModel.AssistedFactory

    private val employeeCardViewModel: EmployeeCardViewModel by viewModels {
        EmployeeCardViewModel.provideFactory(
            employeeCardViewModelFactory,
            args.employeeId
        )
    }

    private var _binding: EmployeeCardFragmentBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EmployeeCardFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        employeeCardViewModel.employee.observe(viewLifecycleOwner, {

            binding.nameTextView.text = "${it.firstName} ${it.lastName}"

            if (it.dateOfBirth != "-") {
                binding.dobTextView.text = "Дата рождения: ${it.dateOfBirth} г."
                binding.ageTextView.text = "Возраст: ${it.calcAge()}"
            } else {
                binding.dobTextView.text = "Дата рождения: ${it.dateOfBirth}"
                binding.ageTextView.text = "Возраст: Неизвестен"
            }

            if (it.employeeAvatar.isNullOrEmpty()) {
                binding.employeeAvatar.setImageResource(R.drawable.ic_baseline_person)
            } else {
                Picasso.get().load(it.employeeAvatar).transform(CircleTransform()).into(binding.employeeAvatar)
            }
        })

        val adapter = SpecialityListAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        employeeCardViewModel.specialities.observe(viewLifecycleOwner, { speciality->
            speciality?.let { adapter.submitList(it) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}