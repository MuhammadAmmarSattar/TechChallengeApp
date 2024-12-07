package com.muhammad.myapplication.forvia.domain.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.muhammad.myapplication.forvia.core.base.Constant.MESSAGE
import com.muhammad.myapplication.forvia.core.base.Constant.TITLE
import com.muhammad.myapplication.forvia.core.presentation.utils.NotificationManagerWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * here little bit different approach for injection, the dependencies
 * we cannot do normal constructor injection in our worker class
 * */

/**
 * Assisted for creating object runtime when we need workerParameters , context dependency needs.
 * */

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                with(NotificationManagerCompat.from(applicationContext)) {
                    notify(
                        0,
                        NotificationManagerWrapper.sendNotification(
                            context,
                            TITLE,
                            MESSAGE
                        )
                    )
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    //The information required when a ListenableWorker runs in the context of a foreground service
    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            0,
            NotificationManagerWrapper.sendNotification(
                context,
                TITLE,
                MESSAGE
            )
        )
    }
}

