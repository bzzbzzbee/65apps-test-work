package com.example.apps65test.ui
/**
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apps65test.data.EmployeesRepository
import com.example.apps65test.data.Speciality


class SpecialitiesViewModelFactory(private val employeesRepository: EmployeesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SpecialitiesViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return SpecialitiesViewModel(employeesRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class EmployeesViewModelFactory(private val employeesRepository: EmployeesRepository, private val speciality: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EmployeesViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return EmployeesViewModel(employeesRepository, speciality) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class EmployeeCardViewModelFactory(private val employeesRepository: EmployeesRepository, private val employeeId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EmployeeCardViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return EmployeeCardViewModel(employeesRepository, employeeId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
*/