package com.example.chatmusc.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatmusc.ChatViewModel
import com.example.chatmusc.MessageModel
import com.example.chatmusc.Navigation.AppScreens
import com.example.chatmusc.R
import com.example.chatmusc.ui.theme.colorModelMessage
import com.example.chatmusc.ui.theme.colorUserMessage

@Composable
fun ChatPage(viewModel: ChatViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Black)
    ) {
        AppHeader(navController = navController)
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.messageList
        )
        MessageInput(
            onMessageSend = {
                viewModel.sendMessage(it)
            }
        )
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>) {
    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_library_music_24),
                contentDescription = "Icon",
                tint = Color.Gray,
            )
            Text(text = "¿Qué quieres sentir hoy?", fontSize = 22.sp, color = Color.White)
        }
    } else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.CenterStart else Alignment.CenterEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48.dp))
                    .background(if (isModel) colorModelMessage else colorUserMessage)
                    .padding(16.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(25.dp) // Ajusta el padding general
            .padding(top = 0.dp), // Ajusta el padding superior aquí
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = { message = it },
            placeholder = { Text("Escribe un mensaje...", color = Color.White) },
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
        )
        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.Default.Send,
                tint = Color.Green,
                contentDescription = "Enviar"
            )
        }
    }
}

@Composable
fun AppHeader(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(20.dp) // Ajusta el padding general
            .padding(top = 40.dp) // Ajusta el padding superior aquí
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "CHAT MUSIC",
                color = Color.White,
                fontSize = 22.sp
            )
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Exit To App",
                tint = Color.White,
                modifier = Modifier.clickable {
                    navController.navigate(route = AppScreens.LoginPage.route)
                }
            )
        }
    }
}
