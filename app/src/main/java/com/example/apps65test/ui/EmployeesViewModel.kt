package com.example.apps65test.ui

import com.squareup.inject.assisted.AssistedInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.apps65test.data.EmployeesRepository
import com.squareup.inject.assisted.Assisted

class EmployeesViewModel @AssistedInject constructor(
    employeesRepository: EmployeesRepository,
    @Assisted speciality: String
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    val employees = employeesRepository.getEmployeesListWithSpeciality(speciality).asLiveData()

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(speciality: String): EmployeesViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            speciality: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(speciality) as T
            }
        }
    }
}