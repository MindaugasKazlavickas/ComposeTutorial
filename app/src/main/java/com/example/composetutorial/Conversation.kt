package com.example.composetutorial

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class Message(val author: String, val body: String)

interface GyroscopeListener {
    fun onRotationChanged(rotationValues: String)
}
class GyroscopeHandler(private val context: Context, private val listener: GyroscopeListener) {
    private val sensorManager: SensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val gyroscopeSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    private var lastRotationValues: String = ""

    private val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // No implementation needed
        }

        override fun onSensorChanged(event: SensorEvent?) {
            event?.let { sensorEvent ->
                if (sensorEvent.sensor.type == Sensor.TYPE_GYROSCOPE) {
                    // Convert radians to degrees
                    val angularVelocityX = String.format("%.2f", Math.toDegrees(sensorEvent.values[0].toDouble()))
                    val angularVelocityY = String.format("%.2f", Math.toDegrees(sensorEvent.values[1].toDouble()))
                    val angularVelocityZ = String.format("%.2f", Math.toDegrees(sensorEvent.values[2].toDouble()))

                    val rotationValues = "Rotation: X=$angularVelocityX°, Y=$angularVelocityY°, Z=$angularVelocityZ°"
                    if (rotationValues != lastRotationValues) {
                        // Update the UI with new gyroscope values only if they've changed
                        listener.onRotationChanged(rotationValues)
                        lastRotationValues = rotationValues

                        // Save the gyroscope values to local storage
                        saveRotationValuesToLocalStorage(rotationValues)
                    }
                }
            }
        }
    }

    fun startListening() {
        gyroscopeSensor?.let { sensor ->
            sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    private fun saveRotationValuesToLocalStorage(rotationValues: String) {
        val sharedPreferences = context.getSharedPreferences("GyroscopeData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("rotationValues", rotationValues).apply()
    }

    fun loadRotationValuesFromLocalStorage(): String {
        val sharedPreferences = context.getSharedPreferences("GyroscopeData", Context.MODE_PRIVATE)
        return sharedPreferences.getString("rotationValues", "") ?: ""
    }
}

@Composable
fun Conversation(
    messages: List<Message>,
    onNavigateToProfile: () -> Unit
) {
    // Initialize rotationValues with values from local storage
    val context = LocalContext.current
    var rotationValues by remember { mutableStateOf("") }

    val gyroscopeHandler = remember(context) {
        GyroscopeHandler(context, object : GyroscopeListener {
            override fun onRotationChanged(newRotationValues: String) {
                // Update the UI with new gyroscope values
                rotationValues = newRotationValues
            }
        })
    }

    // Load gyroscope values from local storage
    LaunchedEffect(Unit) {
        rotationValues = gyroscopeHandler.loadRotationValuesFromLocalStorage()
        gyroscopeHandler.startListening()
    }

    DisposableEffect(Unit) {
        onDispose {
            gyroscopeHandler.stopListening()
        }
    }

    LazyColumn(modifier = Modifier.padding(start = 16.dp)) {
        item {
            Row {
                Button(onClick = { onNavigateToProfile() }) {
                    Text("Go to Profile")
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Update the UI only when gyroscope values change
                Text(rotationValues)
            }
        }
        items(messages) { message ->
            Greeting(message)
        }
    }
}
