package com.yuqiaodan.mydemo.ui.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App

class NotifyActivity : AppCompatActivity() {

    var notifyId = 1

    companion object {
        const val CHANNEL_ID_1 = "10001"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        createNotificationChannel()


        findViewById<View>(R.id.btn_send_notify).setOnClickListener {
            sendNotify()
        }


        findViewById<View>(R.id.btn_send_remote_notify).setOnClickListener {
            sendRemoteNotify()
        }
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID_1, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun sendNotify() {
        createNotificationChannel()
        notifyId += 1
        val intent = Intent(this, AlertActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.mipmap.logo_splash)
            .setContentTitle("通知标题 编号： $notifyId")
            .setContentText("通知内容 编号： $notifyId")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notifyId, builder.build())
        }
    }

    private fun sendRemoteNotify() {
        createNotificationChannel()

        val intent = Intent(this, AlertActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent1: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val pendingIntent2: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notificationLayout =
            RemoteViews(packageName, R.layout.notify_my_first)
        notificationLayout.setOnClickPendingIntent(
            R.id.img,
            pendingIntent1
        )
        notificationLayout.setOnClickPendingIntent(
            R.id.tv,
            pendingIntent2
        )
        val customNotifyBuilder = NotificationCompat.Builder(App.context, CHANNEL_ID_1)
            .setSmallIcon(R.mipmap.logo_splash)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setOngoing(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, customNotifyBuilder.build())
        }

    }
}