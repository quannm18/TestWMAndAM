package com.example.testwmandam.work

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testwmandam.broadcast.MemoryBroadCast
import com.example.testwmandam.utils.delaySleep
import java.util.*

class OneTimeWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val hour = inputData.getInt("hour", -99)
        val minute = inputData.getInt("minute", -99)

        delaySleep()
        return try {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = hour
            calendar[Calendar.MINUTE] = minute
            calendar[Calendar.SECOND] = 0
            val intent = Intent(applicationContext, MemoryBroadCast::class.java)
            intent.putExtra("aaa", "Clean cache")
            intent.putExtra("bbb", "Time $hour:$minute")
            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager =
                applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            Log.e("pending", pendingIntent.toString() + "")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}