package com.example.apps65test.ui


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.apps65test.data.EmployeesRepository
import kotlinx.coroutines.launch


class SpecialitiesViewModel @ViewModelInject internal constructor(employeesRepository: EmployeesRepository) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    val speciality = employeesRepository.getSpecialties().asLiveData()

    init {
        viewModelScope.launch {
            employeesRepository.populateEmployeesDB()
        }
    }
}