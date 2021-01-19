package com.example.apps65test.ui


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.apps65test.data.EmployeesRepository



class SpecialitiesViewModel @ViewModelInject internal constructor(employeesRepository: EmployeesRepository) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    val speciality = employeesRepository.getSpecialties().asLiveData()
}