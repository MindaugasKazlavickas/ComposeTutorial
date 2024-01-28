package com.example.composetutorial

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Conversation(messages: List<Message>, onNavigateToProfile: () -> Unit) {

    LazyColumn {
        item {
            Button(onClick = { onNavigateToProfile() }) {
                Text("Go to Profile")
            }
        }
        items(messages) { message ->
            Greeting(message)
        }
    }
}