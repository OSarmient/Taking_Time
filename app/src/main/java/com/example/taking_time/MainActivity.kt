package com.example.taking_time

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    val myHandler = Handler()
    // Creacion del Canal
    val chanelID = "chat"
    val channelName = "chat"

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Manejar Notificaciones

        // Construir Canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importancia = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(chanelID, channelName, importancia)

            // Manager de Notificaiones
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // Manejar el Switch
        val swN  = findViewById<Switch>(R.id.swNotification)
        swN?.setOnCheckedChangeListener { _, isChecked ->

            val noti = NotificationCompat.Builder(this, chanelID). also {noti ->
                noti.setContentTitle("Taking Time")
                noti.setContentText("Ya ha pasado tiempo")
                noti.setSmallIcon(R.drawable.vector_notification)
            }.build()


            val notificaRunnable : Runnable = object : Runnable {
                override fun run() {
                    val notifiManager = NotificationManagerCompat.from(applicationContext)
                    notifiManager.notify(1, noti)
                    myHandler.postDelayed(this, 30000)
                }
            }

            if (isChecked) {
                notificaRunnable.run()
            } else {
                myHandler.removeCallbacksAndMessages(null)
            }

            val message = if (isChecked) "swNotification:ON" else "swNotification:OFF"
            // notificaRunnable.run() else myHandler.removeCallbacks(notificaRunnable)
            Toast.makeText(
                this@MainActivity, message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}