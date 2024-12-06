package com.muhammad.myapplication.forvia.data.repository

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.muhammad.myapplication.forvia.domain.repository.NotificationRepository
import com.muhammad.myapplication.forvia.domain.worker.NotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Encapsulate the logic of notification scheduling.
 * by using repository pattern.
 * */
class NotificationRepositoryImpl @Inject constructor(
    private val workManager: WorkManager
) : NotificationRepository {
    override fun schedulePeriodicNotification() {
        /**if we need repeated interval e.g after 15 min 30 min etc so we use PeriodicWorkRequestBuilder */
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            15, TimeUnit.MINUTES
        ).build()
        workManager.enqueueUniquePeriodicWork(
            "NotificationInventoryWorker",
            ExistingPeriodicWorkPolicy.UPDATE, /**ensures that if the task is already scheduled, it gets updated with the new workRequest .*/
            workRequest
        )
    }
}