package com.example.walkietalkie.ui.chats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walkietalkie.WalkieTalkieApp
import com.example.walkietalkie.data.local.entity.MessageEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel(private val app: WalkieTalkieApp, conversationId: String) : ViewModel() {
    val messages: StateFlow<List<MessageEntity>> =
        app.messageRepository.observeConversation(conversationId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun send(receiverId: String, conversationId: String, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch { app.messageRepository.sendText(conversationId, receiverId, text) }
    }
}

@Composable
fun ChatScreen(app: WalkieTalkieApp, conversationId: String, receiverId: String) {
    val viewModel = remember(conversationId) { ChatViewModel(app, conversationId) }
    val messages by viewModel.messages.collectAsState()
    var draft by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { msg ->
                Column(Modifier.padding(vertical = 4.dp)) {
                    Text(if (msg.senderId == receiverId) receiverId else "You", style = MaterialTheme.typography.labelSmall)
                    Text(msg.content, style = MaterialTheme.typography.bodyLarge)
                    Text(msg.status.name, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Message") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                viewModel.send(receiverId, conversationId, draft)
                draft = ""
            }) { Text("Send") }
        }
    }
}
