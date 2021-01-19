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
import com.example.apps65test.ui.EmployeeCardViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.employee_card_fragment.*

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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.employee_card_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        employeeCardViewModel.employee.observe(viewLifecycleOwner, {

            nameTextView.text = "${it.firstName} ${it.lastName}"

            if (it.dateOfBirth != "-") {
                dobTextView.text = "Дата рождения: ${it.dateOfBirth} г."
                ageTextView.text = "Возраст: ${it.calcAge()}"
            } else {
                dobTextView.text = "Дата рождения: ${it.dateOfBirth}"
                ageTextView.text = "Возраст: Неизвестен"
            }

            if (it.employeeAvatar != null && it.employeeAvatar != "") {
                Picasso.get().load(it.employeeAvatar).transform(CircleTransform()).into(employeeAvatar)
            } else{
                employeeAvatar.setImageResource(R.drawable.ic_baseline_person)
            }
        })

        val adapter = SpecialityListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        employeeCardViewModel.specialities.observe(viewLifecycleOwner, { speciality->
            speciality?.let { adapter.submitList(it) }
        })
    }

}