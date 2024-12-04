package com.muhammad.myapplication.forvia.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * CommonModule provides global dependencies that are used throughout the application.
 * This module is installed in the SingletonComponent,
 * ensuring that the provided dependencies
 * are available as single instances across the entire app.
 */
@InstallIn(SingletonComponent::class)
@Module
object CommonModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://ws2.aptoide.com/api/6/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



}