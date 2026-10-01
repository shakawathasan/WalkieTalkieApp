package com.example.walkietalkie.ui.walkie

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.walkietalkie.WalkieTalkieApp

@Composable
fun WalkieScreen(
    app: WalkieTalkieApp,
    activeContactId: String
) {
    val repository = app.messageRepository

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Active Contact: $activeContactId")
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                // PTT Transmission logic
            }
        ) {
            Text("Push to Talk")
        }
    }
}
