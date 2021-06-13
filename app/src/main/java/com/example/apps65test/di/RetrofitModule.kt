package com.example.apps65test.di

import com.example.apps65test.api.RetrofitApi
import com.example.apps65test.utilities.EMPLOYEES_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
       return Retrofit.Builder()
           .baseUrl("https://gitlab.65apps.com/65gb/static/raw/master/")
           .addConverterFactory(GsonConverterFactory.create())
           .build()

    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): RetrofitApi {
       return retrofit.create(RetrofitApi::class.java)
    }
}