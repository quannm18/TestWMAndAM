package com.example.testwmandam.utils

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.testwmandam.R
import com.example.testwmandam.broadcast.MemoryBroadCast.Companion.channelID

fun myNoti(title: String, message: String, context: Context) {
    val notification = NotificationCompat.Builder(
        context,"channelID"
    )
        .setSmallIcon(R.color.black)
        .setContentTitle("$title")
        .setContentText("$message")
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .build()
    with(NotificationManagerCompat.from(context)) {
        notify(200, notification)
    }
}

fun delaySleep() {
    try {
        Thread.sleep(3000, 0)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}