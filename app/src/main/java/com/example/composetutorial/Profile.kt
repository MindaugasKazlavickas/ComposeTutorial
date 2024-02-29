package com.example.composetutorial

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Profile(
    onNavigateToConversation: () -> Unit,
    onNavigateToPhotos: () -> Unit,
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf(loadTextFromStorage(context)) }
    var selectedImageUri by remember { mutableStateOf(loadImageUriFromStorage(context)) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImageUri = uri
        if (uri != null) {
            saveImageUriToStorage(uri, context)
        }
    }
    fun launchPhotoPicker() {
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
    Column(modifier = Modifier.padding(start = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()){
            Button(onClick = { onNavigateToConversation() },
                modifier = Modifier.weight(1f)) {
                Text("Go to Conversation")
            }
            Button(onClick = { onNavigateToPhotos() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Camera")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .size(80.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { launchPhotoPicker() }
        ) {
            if (selectedImageUri != null)
                AsyncImage(
                    model = selectedImageUri!!,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
        }
    }
    Row(modifier = Modifier.padding(top = 144.dp)) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            TextField(
                modifier = Modifier.padding(16.dp),
                value = text,
                onValueChange = { newValue ->
                    text = newValue
                    saveTextToStorage(newValue, context)
                }
            )
        }
    }
}

private fun loadTextFromStorage(context: Context): String {
    val sharedPref = context.getSharedPreferences("com.example.composetutorial.PREFERENCES", MODE_PRIVATE)
    return sharedPref.getString("saved_text", "") ?: ""
}

private fun saveTextToStorage(text: String, context: Context) {
    val sharedPref = context.getSharedPreferences("com.example.composetutorial.PREFERENCES", MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("saved_text", text)
        apply()
    }
}

fun loadImageUriFromStorage(context: Context): Uri? {
    val sharedPref = context.getSharedPreferences("com.example.composetutorial.PREFERENCES", MODE_PRIVATE)
    val uriString = sharedPref.getString("copied_image_uri", null)
    return uriString?.let { Uri.parse(it) }
}

fun saveImageUriToStorage(uri: Uri, context: Context) {
    val sharedPref = context.getSharedPreferences("com.example.composetutorial.PREFERENCES", MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("copied_image_uri", uri.toString())
        apply()
    }
}