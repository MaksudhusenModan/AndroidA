package com.omi.birthday

import android.R
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.util.*


class AlarmNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        /*Your lived 29 years 15 days*/

        if (App.sharedPreferences != null && !App.sharedPreferences!!.getString("Name", "")
                .isNullOrBlank()
        ) {

            var msg = "Hey, " + App.sharedPreferences!!.getString("Name", "")
            if (App.isBirthday(App.AgeCalculationValue().getBirthday())) {
                msg = msg + "  Happy Birthday "
            }
            createNotification(msg, App.AgeCalculationValue().dOBResult(), context)
        }
        startAlarm(context, true, true)
    }

    private var notifManager: NotificationManager? = null
    fun createNotification(message: String?, aMessage: String?, context: Context) {
        val NOTIFY_ID = 0 // ID of notification
        val id = "default_channel_id" // default_channel_id
        val title = "Default Channel"
        val intent: Intent
        val pendingIntent: PendingIntent
        val builder: NotificationCompat.Builder
        if (notifManager == null) {
            notifManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            var mChannel = notifManager!!.getNotificationChannel(id)
            if (mChannel == null) {
                mChannel = NotificationChannel(id, title, importance)
                mChannel.enableVibration(true)
                mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                notifManager!!.createNotificationChannel(mChannel)
            }
            builder = NotificationCompat.Builder(context, id)
            intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            builder.setContentTitle(message) // required
                .setSmallIcon(R.drawable.ic_popup_reminder) // required
                .setContentText(aMessage) // required
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(aMessage)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
        } else {
            builder = NotificationCompat.Builder(context, id)
            intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            builder.setContentTitle(message) // required
                .setSmallIcon(R.drawable.ic_popup_reminder) // required
                .setContentText(aMessage) // required
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(aMessage)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)).priority =
                Notification.PRIORITY_HIGH
        }
        val notification = builder.build()
        notifManager!!.notify(NOTIFY_ID, notification)
    }

    fun startAlarm(context: Context, isNotification: Boolean, isRepeat: Boolean) {
        val manager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val myIntent: Intent
        val pendingIntent: PendingIntent

        // SET TIME HERE
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 8// calendar.get(Calendar.HOUR_OF_DAY)
        calendar[Calendar.MINUTE] = 0//calendar.get(Calendar.MINUTE) + 2
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.DATE] = calendar.get(Calendar.DATE) + 1
        myIntent = Intent(context, AlarmNotificationReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0)
        if (!isRepeat) manager[AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000] =
            pendingIntent else manager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}