package com.example.xiaomibackstacktest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

//        val taskBuilder = TaskStackBuilder.create(this).also {
//            it.addParentStack(FirstActivity::class.java)
//            //it.addNextIntent(Intent(this, FirstActivity::class.java))
//            it.addNextIntent(Intent(this, SecondActivity::class.java))
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).run {
                createNotificationChannel(createOtherChannel())
            }
        }

//        val pendingIntent = taskBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT)
//        val taskBuilderIntents = taskBuilder.intents
//        Log.d("TAG", "task builed intents size = ${taskBuilderIntents.size}")
//        taskBuilderIntents.forEach {
//            it.flags
//        }

//        val resultIntent = Intent(this, SecondActivity::class.java)
//        val backIntent = Intent(this, FirstActivity::class.java)
//        backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        val resultPendingIntent = PendingIntent.getActivities(
//            this,
//            0,
//            arrayOf(backIntent, resultIntent),
//            PendingIntent.FLAG_ONE_SHOT
//        )
//
//        val notification = NotificationCompat.Builder(this,
//            MyFirebaseMessagingService.OTHER_CHANNEL_ID
//        )
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Test push title")
//            .setContentText("Test push text")
//            .setAutoCancel(true)
//            .setContentIntent(resultPendingIntent)
//            .build()

        val resultIntent: Intent = Intent(this, FirstActivity::class.java) //
            .putExtra(FirstActivity.ACTIVITY_EXTRA, SecondActivity::class.java.name) //

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this,
            MyFirebaseMessagingService.OTHER_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Test push title")
            .setContentText("Test push text")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(123123, notification)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createOtherChannel(): NotificationChannel {
        val name = "Other"
        val importance = NotificationManager.IMPORTANCE_HIGH
        return NotificationChannel(MyFirebaseMessagingService.OTHER_CHANNEL_ID, name, importance)
    }

}
