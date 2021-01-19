package com.example.apps65test.di

import android.content.Context
import com.example.apps65test.data.EmployeesDao
import com.example.apps65test.data.EmployeesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideEmployeesDatabase(@ApplicationContext context: Context): EmployeesDataBase {
        return EmployeesDataBase.getDatabase(context)
    }

    @Provides
    fun provideEmployeesDao(employeesDataBase: EmployeesDataBase) : EmployeesDao {
        return employeesDataBase.employeesDao()
    }
}