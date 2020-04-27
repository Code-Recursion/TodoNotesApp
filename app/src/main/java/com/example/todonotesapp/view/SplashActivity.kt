package com.example.todonotesapp.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class SplashActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpSharedPreferences()
        checkLoginStatus()
        getFCMToken()
        setupNotification("Welcome to Todo Notes App")
    }

    private fun setupNotification(body: String?) {
        val channelId = "Local ID"
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val NotificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText("Todo Notes App")
                .setContentText(body)
                .setSound(ringtone)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Todo-Notes", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, NotificationBuilder.build())
    }


    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("SplashActivity", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast

                    Log.d("SplashActivity", token)
                    Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })
    }

    private fun setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME,MODE_PRIVATE)

    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        if (isLoggedIn) {
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}