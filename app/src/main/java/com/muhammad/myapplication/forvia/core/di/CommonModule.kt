package com.muhammad.myapplication.forvia.core.di

import android.content.Context
import androidx.room.Room
import com.muhammad.myapplication.forvia.core.base.Constant.BASE_URL
import com.muhammad.myapplication.forvia.data.local.AppInventoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Create a Room database instance named "app.db"
    @Provides //annotation tells Hilt that this function provides a dependency.
    @Singleton
    fun provideStockDatabase(@ApplicationContext context: Context): AppInventoryDatabase =
        Room.databaseBuilder(
            context,
            AppInventoryDatabase::class.java,
            "app.db"
        ).fallbackToDestructiveMigration().build()


}