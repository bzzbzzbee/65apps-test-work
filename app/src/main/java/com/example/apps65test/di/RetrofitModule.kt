package com.example.apps65test.di

import com.example.apps65test.api.RetrofitApi
import com.example.apps65test.api.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): RetrofitApi {
       return RetrofitInstance.api
    }
}