package com.example.walkietalkie.ui.chats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.walkietalkie.WalkieTalkieApp

@Composable
fun ChatScreen(
    app: WalkieTalkieApp,
    conversationId: String,
    receiverId: String
) {
    val repository = app.messageRepository

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Conversation: $conversationId")
        Text("Receiver: $receiverId")
    }
}
