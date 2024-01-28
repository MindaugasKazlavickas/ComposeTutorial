package com.example.composetutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun Profile(onNavigateToConversation: () -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    Column(modifier = Modifier.padding(start = 16.dp)) {
        Button(onClick = { onNavigateToConversation() }) {
            Text("Go to Conversation")
        }
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(2.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(all = 8.dp)) {
            TextField(
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Enter your text") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(modifier = Modifier.padding(all = 8.dp)) {

            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = "Lexi",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "This is my profile!",
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}