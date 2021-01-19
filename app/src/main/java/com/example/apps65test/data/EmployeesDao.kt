package com.example.apps65test.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeesDao {

    @Query("SELECT * FROM employees ORDER BY last_name ASC")
    fun getEmployees(): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE id IN(:eid)")
    fun getEmployeeById(eid:Int): Flow<Employee>

    @Query("SELECT speciality_name FROM speciality ORDER BY speciality_name ASC")
    fun getSpecialities(): Flow<List<String>>

    @Transaction
    @Query("SELECT * FROM speciality WHERE speciality_name IN(:speciality)")
    fun getEmployeesWithCurrentSpeciality(speciality: String): Flow<List<SpecialityWithEmployee>>

    @Transaction
    @Query("SELECT * FROM employees WHERE id IN(:eid)")
    fun getSpecialitiesWithCurrentEmployees(eid: Int): Flow<List<EmployeeWithSpeciality>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(employee:Employee): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(speciality: Speciality)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crossRef: EmployeeSpecialityCrossRef)

    @Query("DELETE FROM employees")
    suspend fun deleteAllEmpl()

    @Query("DELETE FROM employee_speciality_crossref")
    suspend fun deleteAllCross()

    @Query("DELETE FROM speciality")
    suspend fun deleteAllSpec()
}