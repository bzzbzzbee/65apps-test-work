package com.example.apps65test.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.apps65test.data.EmployeesRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class EmployeeCardViewModel @AssistedInject constructor(
    employeesRepository: EmployeesRepository,
    @Assisted employeeId: Int
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    val employee = employeesRepository.getEmployeeById(employeeId).asLiveData()

    val specialities = employeesRepository.getSpecialitiesForEmployee(employeeId).asLiveData()

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(employeeId: Int): EmployeeCardViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            employeeId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(employeeId) as T
            }
        }
    }
}