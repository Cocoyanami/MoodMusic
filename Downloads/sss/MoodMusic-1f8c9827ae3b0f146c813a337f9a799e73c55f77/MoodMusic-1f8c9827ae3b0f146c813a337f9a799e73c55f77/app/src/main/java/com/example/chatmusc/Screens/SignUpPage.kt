package com.example.chatmusc.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.google.firebase.database.DatabaseReference

// Definición de colores personalizados
val GreenColor = Color(0xFF408754)
val DarkGrayColor = Color(0xFF2D2D2D)

@Composable
fun SignUpPage(navController: NavController, database: DatabaseReference) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGrayColor) // Fondo general
            .padding(16.dp)
    ) {
        SignUpHeader(navController = navController)
        BodyContentS(
            modifier = Modifier.padding(top = 16.dp),
            navController = navController,
            database = database
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyContentS(
    modifier: Modifier = Modifier,
    navController: NavController,
    database: DatabaseReference
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent) // Fondo de la sección de registro
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
            colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreenColor,
                unfocusedBorderColor = Color.Gray
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            textStyle = TextStyle(color = Color.White), // Texto blanco
            colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreenColor,
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
            visualTransformation = PasswordVisualTransformation(),
            colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreenColor,
                unfocusedBorderColor = Color.Gray
            )
        )
        Button(
            onClick = {
                if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    val user = User(username, email, password)
                    database.child("Users").child(username).setValue(user)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show()
                                navController.navigate(route = AppScreens.ChatPage.route)
                            } else {
                                Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
                                Log.e("Firebase", "Registration failed: ${task.exception?.message}")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                            Log.e("Firebase", "Error writing to database", exception)
                        }
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = GreenColor)
        ) {
            Text("Sign Up", color = Color.White)
        }
        Button(
            onClick = {
                navController.navigate(route = AppScreens.LoginPage.route)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Back to Login", color = Color.White)
        }
    }
}

data class User(val username: String, val email: String, val password: String)

@Composable
fun SignUpHeader(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(GreenColor) // Fondo del encabezado en verde
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 22.sp,
                style = MaterialTheme.typography.headlineMedium
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                tint = Color.White,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(8.dp)
            )
        }
    }
}
