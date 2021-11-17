package com.example.pushopennotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    lateinit var recyclerbtn : Button
    lateinit var socialbtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        socialbtn = findViewById(R.id.Social)
        recyclerbtn = findViewById(R.id.recyclerviewbtn)
        if(intent.extras != null){
            var i = Intent(this,Social::class.java)
            startActivity(i)
        }
        socialbtn.setOnClickListener {
            var i = Intent(this,Social::class.java)
            startActivity(i)
        }
        recyclerbtn.setOnClickListener {
            var i = Intent(this,NotificationlistActivity::class.java)
            startActivity(i)
        }
    }
}