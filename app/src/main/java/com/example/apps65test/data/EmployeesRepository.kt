package com.example.apps65test.data

import android.annotation.SuppressLint
import android.util.Log
import com.example.apps65test.api.RetrofitApi
import com.example.apps65test.utilities.ALL_SPECIALITIES_TEXT
import com.example.apps65test.utilities.EMPLOYEES_URL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeesRepository @Inject constructor(private val employeesDao: EmployeesDao, private val retrofitApi: RetrofitApi) {


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

    //TODO в инит блоке реализовать заполнение БД
    suspend fun populateEmployeesDB() {
        try {
            val response = retrofitApi.loadResponse(EMPLOYEES_URL)

            if (response.isSuccessful) {
                val employees = response.body()?.response

                employees?.forEach outer@{ employee ->
                    val emp = Employee(
                        employee.f_name?.toLowerCase(getDefault())?.capitalize(getDefault()),
                        employee.l_name?.toLowerCase(getDefault())?.capitalize(getDefault()),
                        dateValid(employee.birthday),
                        employee.avatr_url
                    )
                    val emp_id = employeesDao.insert(emp).toInt()

                    employee.specialty?.forEach inner@{ specialty ->
                        if(emp_id == -1) return@inner
                        val speciality = Speciality(
                            specialty.specialty_id,
                            specialty.name
                        )
                        employeesDao.insert(speciality)
                        val emp_spec_ref = EmployeeSpecialityCrossRef(emp_id, speciality.id)
                        employeesDao.insert(emp_spec_ref)
                    }
                }
            } else {
                throw IOException(response.errorBody().toString())
            }
        }catch (t: Throwable){
            Log.e("Db populating ex: ", t.message.toString())
        }
    }
}


        @SuppressLint("SimpleDateFormat")
       private fun dateValid(date: String?, sdf: SimpleDateFormat): String {
            val format = SimpleDateFormat("dd.MM.yyyy")
            var testDate: Date? = null
            try {
                testDate = sdf.parse(date)
            } catch (t: Throwable) {
                return "-"
            }
            if (sdf.format(testDate) != date) {
                return "-"
            }
            return format.format(sdf.parse(date))
        }

        @SuppressLint("SimpleDateFormat")
        private fun dateValid(date: String?): String{
            val sdf1 = SimpleDateFormat("dd-MM-yyyy")
            val sdf2 = SimpleDateFormat("yyyy-MM-dd")

            return if (dateValid(date, sdf1) != "-") {
                dateValid(date, sdf1)
            } else {
                dateValid(date, sdf2)
            }
        }