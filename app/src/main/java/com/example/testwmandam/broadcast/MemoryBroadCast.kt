package com.example.testwmandam.broadcast

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.testwmandam.R
import com.example.testwmandam.views.view.activity.ReceiverActivity

class MemoryBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val repeating_Intent = Intent(context, ReceiverActivity::class.java)
        repeating_Intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            repeating_Intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        var builder: NotificationCompat.Builder? = null
        builder = if (intent != null && intent.getStringExtra("aaa") != null) {
            NotificationCompat.Builder(context!!, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.beer_notification)
                .setLargeIcon(
                    Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(
                            context!!.resources,
                            R.drawable.thinkman1
                        ), 128, 128, false
                    )
                )
                .setContentTitle(intent.getStringExtra("aaa"))
                .setContentText(intent.getStringExtra("bbb"))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
        } else {
            NotificationCompat.Builder(context!!, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.beer_notification)
                .setLargeIcon(
                    Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(
                            context!!.resources,
                            R.drawable.thinkman1
                        ), 128, 128, false
                    )
                )
                .setContentTitle("PASTICCINO")
                .setContentText("This is a daily notification")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
        }

        Log.e("anc", intent!!.extras.toString() + "")

        val notificationManager = NotificationManagerCompat.from(context!!)

        notificationManager.notify(200, builder.build())
    }
    companion object {
        const val notificationID = 1
        const val channelID = "channel1"
        const val titleExtra = "titleExtra"
        const val messageExtra = "messageExtra"
    }
}