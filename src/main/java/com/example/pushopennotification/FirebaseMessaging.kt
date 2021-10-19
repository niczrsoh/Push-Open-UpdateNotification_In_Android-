package com.example.pushopennotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Exception

class FirebaseMessaging : FirebaseMessagingService() {
        lateinit var title: String
        lateinit var message: String
        var CHANNEL_ID ="CHANNEL"
        lateinit var manager: NotificationManager

        // receive message function
        override fun onMessageReceived(remoteMessage: RemoteMessage) {
                super.onMessageReceived(remoteMessage)
                title = remoteMessage.notification!!.title!!
                message = remoteMessage.notification!!.body!!
                manager = this.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
                sendNotification()
        }

        override fun onNewToken(p0: String) {
                super.onNewToken(p0)
        }
        //send notification function
        private fun sendNotification() {
                //set the notification tap action
                //to open an activity in your app that corresponds to the notification.
                var intent : Intent

                intent = Intent(applicationContext, NotificationlistActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                var pendingIntent: PendingIntent = PendingIntent.getActivity(
                        applicationContext,
                        0, intent, PendingIntent.FLAG_ONE_SHOT
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        var channel = NotificationChannel(
                                CHANNEL_ID, "pushnotification", NotificationManager.IMPORTANCE_HIGH
                        )
                        manager.createNotificationChannel(channel)
                }

                //set the notification's content
                var builder = NotificationCompat.Builder(this,CHANNEL_ID)
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setColor(Color.TRANSPARENT)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                //show the notification

                manager.notify(0, builder.build())
        }
}
