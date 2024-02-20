package com.example.composetutorial

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import kotlinx.coroutines.*
import android.app.*
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import androidx.core.app.ActivityCompat
import android.content.*
import android.hardware.*
import android.os.*
import androidx.activity.*
import androidx.core.app.*
import android.content.BroadcastReceiver
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Navigation()
            }
        }

    }
}
@Preview
@Composable
fun ConversationPreview() {
    ComposeTutorialTheme {
        Navigation()
    }
}