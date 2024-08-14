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
            .background(Color.DarkGray) // Cambiado a gris oscuro
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
            Text(text = "¿Qué quieres sentir hoy?", fontSize = 22.sp, color = Color(0xFFE0E0E0)) // Gris claro
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
    val processedText = processTextForBold(messageModel.message)

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
                        text = processedText,
                        fontWeight = FontWeight.W500,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(16.dp) // Reducido el padding general
            .padding(top = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = { message = it },
            placeholder = { Text("Escribe un mensaje...", color = Color(0xFFE0E0E0)) }, // Gris claro
            textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFFE0E0E0)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent, // Sin borde cuando enfocado
                unfocusedBorderColor = Color.Transparent // Sin borde cuando no enfocado
            )
        )
        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.Default.Send,
                tint = Color(0xFF4CAF50), // Verde más suave
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
            .background(Color.DarkGray) // Cambiado a gris oscuro
            .padding(16.dp) // Reducido el padding general
            .padding(top = 20.dp) // Reducido el padding superior
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), // Reducido
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "CHAT MUSIC",
                color = Color(0xFFE0E0E0), // Gris claro
                fontSize = 20.sp, // Reducido
                fontWeight = FontWeight.W400 // Menos negrita
            )
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Exit To App",
                tint = Color(0xFFE0E0E0),
                modifier = Modifier.clickable {
                    navController.navigate(route = AppScreens.LoginPage.route)
                }
            )
        }
    }
}
