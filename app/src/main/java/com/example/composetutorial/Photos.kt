package com.example.composetutorial

import android.content.ContentValues.TAG
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.camera.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.MutableState
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import coil.compose.rememberImagePainter
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Photos(
    onNavigateToProfile: () -> Unit,
    lifecycleOwner: LifecycleOwner,
    imageFilesState: MutableState<List<File>>
) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }

    Column(modifier = Modifier.padding(start = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()){
            Box(modifier = Modifier.weight(1f))
            Button(onClick = { onNavigateToProfile() },
                modifier = Modifier.weight(1f)) {
                Text("Go to Profile")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row{
            Button(onClick = { TakePhoto(lifecycleOwner, context, previewView, imageFilesState) }) {
                Text("Take Photo")
            }
        }
        LazyColumn(){
            item(){
                CameraPreview(previewView, lifecycleOwner)
            }
            item() {
                Spacer(modifier = Modifier.height(64.dp))
            }
            item(){
                Text("Previously taken photos")
            }
            items(imageFilesState.value
                .windowed(1, step = 1)) { imageFile ->
                    imageFile.forEach { imageFile ->
                    Image(
                        painter = rememberImagePainter(imageFile),
                        contentDescription = null,
                        modifier = Modifier
                            .height(150.dp)
                            //.weight(1f)
                            .padding(horizontal = 4.dp)
                            )
                    }
            }
        }
    }
}

fun getImageFiles(context: Context): List<File> {
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFiles = outputDirectory?.listFiles { file ->
        file.isFile && file.extension.lowercase(Locale.getDefault()) == "jpg"
    }
    return imageFiles?.toList()?.sortedByDescending { it.lastModified() } ?: emptyList()
}

fun TakePhoto(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    previewView: PreviewView,
    imageFilesState: MutableState<List<File>>
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        try {

            val imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                imageCapture
            )

            val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
            val photoFile = File(
                outputDirectory,
                SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                    .format(System.currentTimeMillis()) + ".jpg"
            )

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedUri = Uri.fromFile(photoFile)
                        val msg = "Photo capture succeeded: $savedUri"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

                        val updatedImageFiles = getImageFiles(context)
                        imageFilesState.value = updatedImageFiles

                        val preview = Preview.Builder().build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
                    }

                    override fun onError(exc: ImageCaptureException) {
                        Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                    }
                })

        } catch (exc: Exception) {
            exc.printStackTrace()
        }

    }, ContextCompat.getMainExecutor(context))
}