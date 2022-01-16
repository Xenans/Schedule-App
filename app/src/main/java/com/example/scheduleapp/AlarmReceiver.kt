package com.example.scheduleapp

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// Receives all notifications from app and broadcasts them out to user
class AlarmReceiver : BroadcastReceiver() {
    private var id: Int = 0         // ID of notification
    private var rc: Int = 0         // Request code for Intent

    // Callback for when notification is received
    override fun onReceive(context: Context, intent: Intent) {
        // Setup Intent so that when notification is clicked, it opens a new Activity
        val newIntent = Intent(context, NotificationActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)       // why do this?

        lateinit var pendingIntent: PendingIntent
        if (Build.VERSION.SDK_INT >= 23) {
            pendingIntent = PendingIntent.getActivity(context, this.rc, newIntent, PendingIntent.FLAG_IMMUTABLE)        // when Version >= 23, need to include mutability flag
        } else {
            pendingIntent = PendingIntent.getActivity(context, this.rc, newIntent, 0)
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Schedule App")
            .setContentText(intent.getStringExtra("notificationMessage"))
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        // Show the notification
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(this.id, builder.build())

        // Increment id and rc
        incrementId()
        incrementRc()
    }

    // Increments the value of id
    private fun incrementId() {
        id++
    }

    // Increments the value of rc
    private fun incrementRc() {
        rc++
    }
}