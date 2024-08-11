package com.example.chatmusc.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatmusc.ChatViewModel
import com.example.chatmusc.Screens.ChatPage
import com.example.chatmusc.Screens.LoginPage
import com.example.chatmusc.Screens.SignUpPage
import com.google.firebase.database.FirebaseDatabase

@Composable
fun AppNavigation() {
    val chatViewModel: ChatViewModel = viewModel()
    val navController = rememberNavController()

    val database = remember { FirebaseDatabase.getInstance().reference }

    NavHost(navController = navController, startDestination = AppScreens.SignUpPage.route) {
        composable(route = AppScreens.LoginPage.route) {
            LoginPage(navController)
        }
        composable(route = AppScreens.SignUpPage.route) {
            SignUpPage(navController = navController, database = database)
        }
        composable(route = AppScreens.ChatPage.route) {
            ChatPage(viewModel = chatViewModel, navController = navController)
        }
    }
}
