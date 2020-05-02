package com.witnovus.placefinder.jobscheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.witnovus.placefinder.R

class NotificationService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    private fun startNotification(params: JobParameters) {
        val notificationMessage = params.getExtras().getString("notificationMessage")!!
        Log.d("Set Notification", "notificationMessage" + notificationMessage)
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setAutoCancel(true)
            .setContentTitle("App Reminder")
            .setContentText(notificationMessage)
            .setSmallIcon(R.mipmap.ic_launcher)

        val mNotificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val mChannel = NotificationChannel(
                params.jobId.toString(),
                params.jobId.toString(),
                NotificationManager.IMPORTANCE_HIGH
            )

            mNotificationManager.createNotificationChannel(mChannel)
            mBuilder.setChannelId(params.jobId.toString())

        }
        mNotificationManager.notify(params.jobId, mBuilder.build())


    }

    override fun onStartJob(params: JobParameters?): Boolean {
        if (params != null) {
            if (params.getExtras().getString("notificationMessage") != null) {
                startNotification(params)
            }
        }
        return true
    }

}