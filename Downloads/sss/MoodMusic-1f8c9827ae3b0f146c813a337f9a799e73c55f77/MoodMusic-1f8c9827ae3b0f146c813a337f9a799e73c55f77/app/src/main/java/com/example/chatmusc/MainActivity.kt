package com.example.chatmusc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chatmusc.Navigation.AppNavigation
import com.example.chatmusc.ui.theme.ChatMuscTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        database = FirebaseDatabase.getInstance().reference

        setContent {
            ChatMuscTheme{
                AppNavigation()
            }
        }
    }
}