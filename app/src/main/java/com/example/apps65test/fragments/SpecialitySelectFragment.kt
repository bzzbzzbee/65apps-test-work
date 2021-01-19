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
import com.example.apps65test.ui.SpecialitiesViewModel
import com.example.apps65test.utilities.ALL_SPECIALITIES_TEXT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.specialities_list_fragment.*

@AndroidEntryPoint
class SpecialitySelectFragment : Fragment() {
    private val specialitiesViewModel: SpecialitiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.specialities_list_fragment, container, false)
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

        specialitySpinner.adapter = adapter
        specialitySpinner.prompt = "Выберите специальность"
        specialitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (counter > 0){
                    val selected = parent!!.getItemAtPosition(position).toString()
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

}