package com.muhammad.myapplication.forvia.domain.use_case

import com.muhammad.myapplication.forvia.domain.repository.NotificationRepository
import javax.inject.Inject

class ScheduleNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    fun execute() {
        notificationRepository.schedulePeriodicNotification()
    }
}
