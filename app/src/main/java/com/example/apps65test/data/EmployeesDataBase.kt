package com.example.apps65test.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.apps65test.utilities.DATABASE_NAME
import com.example.apps65test.utilities.EMPLOYEES_URL
import com.example.apps65test.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault


@Database(
    entities = [Employee::class, Speciality::class, EmployeeSpecialityCrossRef::class],
    version = 1,
    exportSchema = false
)

abstract class EmployeesDataBase : RoomDatabase() {

    abstract fun employeesDao(): EmployeesDao

    companion object {
        @Volatile
        private var INSTANCE: EmployeesDataBase? = null

        fun getDatabase(context: Context): EmployeesDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeesDataBase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(EmployeeDatabaseCallback(GlobalScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class EmployeeDatabaseCallback(
            private val scope: GlobalScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateEmployeesDB(database.employeesDao())
                    }
                }
            }
        }

        suspend fun populateEmployeesDB(employeesDao: EmployeesDao) {
            try {
                val response = RetrofitInstance.api.loadResponse(EMPLOYEES_URL)

                if (response.isSuccessful) {
                    val employees = response.body()?.response

                    employees?.forEach { employee ->
                        val emp = Employee(
                            employee.f_name?.toLowerCase(getDefault())!!.capitalize(getDefault()),
                            employee.l_name?.toLowerCase(getDefault())!!.capitalize(getDefault()),
                            dateValid(employee.birthday),
                            employee.avatr_url
                        )
                        val emp_id = employeesDao.insert(emp).toInt()

                        employee.specialty?.forEach { specialty ->
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
            }catch (e: java.lang.Exception){
                Log.e("Db populating ex: ", e.cause.toString())
            }
        }

        @SuppressLint("SimpleDateFormat")
       private fun dateValid(date: String?, sdf: SimpleDateFormat): String {
            val format = SimpleDateFormat("dd.MM.yyyy")
            var testDate: Date? = null
            try {
                testDate = sdf.parse(date)
            } catch (e: Exception) {
                return "-"
            }
            if (sdf.format(testDate) != date) {
                return "-"
            }
            return format.format(sdf.parse(date))
        }

        //Честно говоря немного не понятно то что от api прихоядт даты в разных форматах, надо ли их все учитывать либо только dd.MM.yyyy
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
    }
}