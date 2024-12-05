package com.muhammad.myapplication.forvia.data.di

import com.muhammad.myapplication.forvia.data.local.AppInventoryDao
import com.muhammad.myapplication.forvia.data.local.AppInventoryDatabase
import com.muhammad.myapplication.forvia.data.remote.AppInventoryRDS
import com.muhammad.myapplication.forvia.data.remote.service.AppInventoryService
import com.muhammad.myapplication.forvia.data.repository.AppInventoryRepositoryImpl
import com.muhammad.myapplication.forvia.domain.repository.AppInventoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideAppService(retrofit: Retrofit): AppInventoryService {
        return retrofit.create(AppInventoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppInventoryRDS(appService: AppInventoryService): AppInventoryRDS {
        return AppInventoryRDS(appService = appService)
    }

    @Provides
    fun provideExploreRepository(appInventoryRDS: AppInventoryRDS ): AppInventoryRepository =
        AppInventoryRepositoryImpl(appInventoryRDS = appInventoryRDS )

    @Provides
    fun provideAppDao(appDatabase: AppInventoryDatabase): AppInventoryDao =
        appDatabase.getAppInventoryDao()

}