/*package com.example.composetutorial

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect

@Composable
fun PhotoSelectorView(context: Context, maxSelectionCount: Int = 1) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(Unit) {
        selectedImageUri = loadImageUriFromStorage(context)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            if (uri != null) {
                saveImageUriToStorage(uri, context)
            }
        }
    )

    fun launchPhotoPicker() {
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier
                .size(80.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { launchPhotoPicker() }
        ) {
            item {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(80.dp)
                        .clip(CircleShape)
                        .clickable { launchPhotoPicker() },
                    contentScale = ContentScale.Crop
                )
            }
        }
        selectedImageUri != null
    }
}

fun loadImageUriFromStorage(context: Context): Uri? {
    val sharedPref = context.getSharedPreferences("com.example.composetutorial.PREFERENCES", MODE_PRIVATE)
    val uriString = sharedPref.getString("copied_image_uri", null)
    return uriString?.let { Uri.parse(it) }
}

fun saveImageUriToStorage(uri: Uri, context: Context) {
    val sharedPref =
        context.getSharedPreferences("com.example.composetutorial.PREFERENCES", MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("copied_image_uri", uri.toString())
        apply()
    }
}*/