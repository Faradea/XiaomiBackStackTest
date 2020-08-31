package com.example.xiaomibackstacktest

import android.R.attr.label
import android.app.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val OTHER_CHANNEL_ID = "com_example_xiaomibackstacktest_notification_channels_other_low_importance"
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        Log.d("TAG", "onMessageReceived, message = $p0")

        val taskBuilder = TaskStackBuilder.create(this).also {
            it.addNextIntent(Intent(this, FirstActivity::class.java))
            it.addNextIntent(Intent(this, SecondActivity::class.java))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).run {
                createNotificationChannel(createOtherChannel())
            }
        }

        val pendingIntent = taskBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT)
        val taskBuilderIntents = taskBuilder.intents
        Log.d("TAG", "task builed intents size = ${taskBuilderIntents.size}")
        taskBuilderIntents.forEach {
            it.flags
        }

        val notification = NotificationCompat.Builder(this, OTHER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Test push title")
            .setContentText("Test push text")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(123123, notification)

    }

    override fun onNewToken(token: String?) {
        Log.d("TAG", "Refreshed token: $token")

//        val clipboard: ClipboardManager =
//            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        val clip = ClipData.newPlainText(label.toString(), token)
//        clipboard.setPrimaryClip(clip)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createOtherChannel(): NotificationChannel {
        val name = "Other"
        val importance = NotificationManager.IMPORTANCE_HIGH
        return NotificationChannel(OTHER_CHANNEL_ID, name, importance)
    }

}