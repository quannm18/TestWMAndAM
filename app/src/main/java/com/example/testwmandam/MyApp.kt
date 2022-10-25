package com.example.testwmandam

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notify Channel"
            val desc = "A description of the Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channelID", name, importance)
            channel.description = desc

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}