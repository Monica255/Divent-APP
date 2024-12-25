package com.example.divent.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.divent.R
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.data.source.repository.EventRepository
import com.example.divent.ui.content.detail.DetailActivity
import com.example.divent.ui.content.favorite.FavoriteFragment.Companion.ID
import com.example.divent.util.EVENT
import com.example.divent.util.NOTIFICATION_CHANNEL_ID
import com.example.divent.util.NOTIFICATION_CHANNEL_NAME
import com.example.divent.util.NOTIFICATION_ID
import com.example.divent.util.executeThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DailyReminderWorker(
    private val context: Context,
    private val repository: EventRepository,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        executeThread {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val data = repository.getEvent2(EVENT.UPCOMING, limit = 1)
                    data?.let {
                        showNotification(context, it)
                    }

                }
            }catch (e:Exception){
                Log.d("ERROR", e.message.toString())
            }
        }
        return Result.success()
    }

    private fun showNotification(context: Context,event:Event) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationStyle = NotificationCompat.InboxStyle()
        notificationStyle.addLine(event.name)
        notificationStyle.addLine(event.beginTime)

        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_notif)
                .setContentTitle(context.resources.getString(R.string.next_event))
                .setStyle(notificationStyle)
                .setContentIntent(getPendingIntent(context,event.id))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notification.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    private fun getPendingIntent(context: Context,id:Int): PendingIntent? {
//        val parent = Intent(context, HomeActivity::class.java)
        val intent = Intent(context, DetailActivity::class.java).putExtra(ID,id)
        return TaskStackBuilder.create(context).run {
//            addNextIntent(parent)
            addNextIntent(intent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE )
        }
    }

    class Factory @Inject constructor(
        val repository: EventRepository,
    ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): Worker {
            return DailyReminderWorker(appContext,repository, params)
        }
    }
}
