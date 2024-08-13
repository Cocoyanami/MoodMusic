package com.example.chatmusc.Screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatmusc.Navigation.AppScreens

// Definición de colores personalizados



@Composable
fun LoginPage(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF2D2D2D) // Fondo de la página
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()
            BodyContentL(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyContentL(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("Loading...") }
    val context = LocalContext.current

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

    if (temperatureSensor == null) {
        temperature = "Sensor not available"
    } else {
        DisposableEffect(context) {
            val sensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                        val temp = event.values[0]
                        temperature = if (temp in -50f..50f) {
                            String.format("%.1f°C", temp)
                        } else {
                            "Invalid reading"
                        }
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            sensorManager.registerListener(sensorEventListener, temperatureSensor, SensorManager.SENSOR_DELAY_UI)
            onDispose {
                sensorManager.unregisterListener(sensorEventListener)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username") },
            textStyle = TextStyle(color = Color.White), // Texto blanco
            singleLine = true,
            colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF408754),
                unfocusedBorderColor = Color.Gray
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            textStyle = TextStyle(color = Color.White), // Texto blanco
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF408754),
                unfocusedBorderColor = Color.Gray
            )
        )
        Button(
            onClick = {
                navController.navigate(route = AppScreens.ChatPage.route)
                Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF408754))
        ) {
            Text("Login", color = Color.White)
        }
        Button(
            onClick = {
                navController.navigate(route = AppScreens.SignUpPage.route)
                Toast.makeText(context, "Sign Up", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Sign Up", color = Color.White)
        }
        Text(
            text = temperature,
            style = TextStyle(color = Color.White, fontSize = 14.sp),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun LoginHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF408754)) // Fondo del encabezado en verde
            .padding(12.dp)
    ) {
        Text(
            text = "Login",
            color = Color.White,
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
