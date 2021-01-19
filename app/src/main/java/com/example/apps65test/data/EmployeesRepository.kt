package com.example.apps65test.data

import com.example.apps65test.utilities.ALL_SPECIALITIES_TEXT
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeesRepository @Inject constructor(private val employeesDao: EmployeesDao) {

    fun getSpecialties() = employeesDao.getSpecialities()

    fun getEmployeesListWithSpeciality(speciality: String): Flow<List<Employee>> {
        return if(speciality == ALL_SPECIALITIES_TEXT) {
            employeesDao.getEmployees()
        } else {
            employeesDao.getEmployeesWithCurrentSpeciality(speciality).transform {
                it.forEach { empList->
                    emit(empList.employees)
                }
            }
        }
    }

    fun getSpecialitiesForEmployee(id: Int): Flow<List<Speciality>> {
        return employeesDao.getSpecialitiesWithCurrentEmployees(id).transform {
            it.forEach { empWithSpecialityList->
                emit(empWithSpecialityList.specialities)
            }
        }
    }

    fun getEmployeeById(eid: Int) = employeesDao.getEmployeeById(eid)
}
