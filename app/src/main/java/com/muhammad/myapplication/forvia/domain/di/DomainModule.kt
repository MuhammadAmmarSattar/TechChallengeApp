package com.muhammad.myapplication.forvia.domain.di

import com.muhammad.myapplication.forvia.domain.repository.AppInventoryRepository
import com.muhammad.myapplication.forvia.domain.use_case.AppInventoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}