package com.example.chatmusc.Navigation

sealed class AppScreens (val route: String){
    object LoginPage: AppScreens("login_page")
    object SignUpPage: AppScreens("signup_page")
    object ChatPage: AppScreens("chat_page")
}