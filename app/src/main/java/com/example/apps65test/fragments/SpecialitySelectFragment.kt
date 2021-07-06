package com.example.apps65test.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.apps65test.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apps65test.databinding.SpecialitiesListFragmentBinding
import com.example.apps65test.ui.SpecialitiesViewModel
import com.example.apps65test.utilities.ALL_SPECIALITIES_TEXT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialitySelectFragment : Fragment() {
    private val specialitiesViewModel: SpecialitiesViewModel by viewModels()
    private var _binding: SpecialitiesListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SpecialitiesListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onStart() {
        super.onStart()

        var counter = 0

        val adapter =
            ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item)
        adapter.add("Ничего не выбрано")
        adapter.add(ALL_SPECIALITIES_TEXT)

        specialitiesViewModel.speciality.observe(viewLifecycleOwner, { speciality ->
            speciality?.forEach { spec->
                adapter.add(spec)
            }
        })

        binding.specialitySpinner.adapter = adapter
        binding.specialitySpinner.prompt = "Выберите специальность"
        binding.specialitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (counter > 0){
                    val selected = parent?.getItemAtPosition(position).toString()
                    val direction =
                        SpecialitySelectFragmentDirections.actionSpecialitySelectFragmentToEmployeesListFragment(
                            selected
                        )
                    findNavController().navigate(direction)
                }
                counter++
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}