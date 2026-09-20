package com.forexalertassistant

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val channelId = "forex_alerts"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
            setBackgroundColor(Color.WHITE)
        }

        val title = TextView(this).apply {
            text = "Forex Alert Assistant"
            textSize = 28f
            setTextColor(Color.rgb(25, 25, 25))
        }

        val subtitle = TextView(this).apply {
            text = "Forex only • Alerts only"
            textSize = 16f
            setPadding(0, 12, 0, 28)
        }

        val status = TextView(this).apply {
            text = "WAIT\n\nNo confirmed setup yet.\n\nSignal framework:\n• Trend\n• Support / Resistance\n• Breakout + retest\n• RSI\n• Moving averages\n• Candlestick confirmation\n• Multiple timeframes"
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(20, 40, 20, 40)
            setTextColor(Color.DKGRAY)
        }

        val note = TextView(this).apply {
            text = "This prototype does not place trades automatically."
            textSize = 14f
            setTextColor(Color.GRAY)
        }

        root.addView(title)
        root.addView(subtitle)
        root.addView(status)
        root.addView(note)
        setContentView(root)

        requestNotificationPermission()
        showStartupNotification()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Forex Alerts",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
    }

    private fun showStartupNotification() {
        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Forex Alert Assistant")
            .setContentText("Alert system is ready. Current signal: WAIT.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(1, notification)
    }
}
