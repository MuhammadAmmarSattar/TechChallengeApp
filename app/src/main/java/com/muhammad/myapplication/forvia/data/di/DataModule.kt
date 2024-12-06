package com.muhammad.myapplication.forvia.data.di

import androidx.work.WorkManager
import com.muhammad.myapplication.forvia.data.local.AppInventoryDao
import com.muhammad.myapplication.forvia.data.local.AppInventoryDatabase
import com.muhammad.myapplication.forvia.data.local.AppInventoryLDS
import com.muhammad.myapplication.forvia.data.remote.AppInventoryRDS
import com.muhammad.myapplication.forvia.data.remote.service.AppInventoryService
import com.muhammad.myapplication.forvia.data.repository.AppInventoryRepositoryImpl
import com.muhammad.myapplication.forvia.data.repository.NotificationRepositoryImpl
import com.muhammad.myapplication.forvia.domain.repository.AppInventoryRepository
import com.muhammad.myapplication.forvia.domain.repository.NotificationRepository
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
    fun provideExploreRepository(
        appInventoryRDS: AppInventoryRDS,
        appInventoryLDS: AppInventoryLDS
    ): AppInventoryRepository =
        AppInventoryRepositoryImpl(
            appInventoryRDS = appInventoryRDS,
            appInventoryLDS = appInventoryLDS
        )

    @Provides
    fun provideAppDao(appDatabase: AppInventoryDatabase): AppInventoryDao =
        appDatabase.getAppInventoryDao()

    @Provides
    @Singleton
    fun provideNotificationRepository(
        workManager: WorkManager
    ): NotificationRepository {
        return NotificationRepositoryImpl(workManager)
    }

}