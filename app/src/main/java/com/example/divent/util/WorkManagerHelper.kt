package com.example.divent.util

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.divent.core.notification.DailyReminderWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit

object WorkManagerHelper {
    private const val TAG_DAILY_REMINDER = "DailyReminder"

    fun setDailyReminder(context: Context,hour: Int,minute: Int) {
        val workRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(hour,minute), TimeUnit.MILLISECONDS)
            .addTag(TAG_DAILY_REMINDER)  // Set the tag here
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    // Calculate the initial delay until the reminder time
    private fun calculateInitialDelay(hour:Int,minute:Int): Long {
        val calendar = Calendar.getInstance()
        val currentTime = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.timeInMillis < currentTime) {
            calendar.add(Calendar.DATE, 1) // Set untuk besok jika waktu sudah lewat
        }

        return calendar.timeInMillis - currentTime
    }

    fun cancelDailyReminder(context: Context) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(TAG_DAILY_REMINDER)
    }
}
