package com.muhammad.myapplication.forvia.domain.di

import android.content.Context
import androidx.work.WorkManager
import com.muhammad.myapplication.forvia.domain.repository.AppInventoryRepository
import com.muhammad.myapplication.forvia.domain.repository.NotificationRepository
import com.muhammad.myapplication.forvia.domain.use_case.AppInventoryUseCase
import com.muhammad.myapplication.forvia.domain.use_case.ScheduleNotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    //Provides a singleton instance of AppInventoryUseCase
    @Provides
    @Singleton
    fun provideAppDataUseCase(repository: AppInventoryRepository): AppInventoryUseCase {
        return AppInventoryUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideScheduleNotificationUseCase(
        notificationRepository: NotificationRepository
    ): ScheduleNotificationUseCase {
        return ScheduleNotificationUseCase(notificationRepository)
    }




}