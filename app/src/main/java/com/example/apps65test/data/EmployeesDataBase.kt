package com.example.apps65test.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Employee::class, Speciality::class, EmployeeSpecialityCrossRef::class],
    version = 1,
    exportSchema = false
)

abstract class EmployeesDataBase : RoomDatabase() {
    abstract fun employeesDao(): EmployeesDao
}