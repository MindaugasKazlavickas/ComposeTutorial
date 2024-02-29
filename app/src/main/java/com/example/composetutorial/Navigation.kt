package com.example.composetutorial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "conversation") {
        composable("conversation") {
            Conversation(
                SampleData.conversationSample,
                onNavigateToProfile = {
                    navController.navigate("profile") {
                        popUpTo("profile") {
                            inclusive = true
                        }
                    }
                })
        }
        composable("profile") {
            Profile(
                onNavigateToConversation = {
                    navController.navigate("conversation")
                },
                onNavigateToPhotos = {
                    navController.navigate("photos")
                },

            )
        }


        composable("photos") { backStackEntry ->
            val lifecycleOwner = LocalLifecycleOwner.current
            val context = LocalContext.current
            val imageFilesState = rememberSaveable { mutableStateOf(getImageFiles(context)) }
            Photos(
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                lifecycleOwner = lifecycleOwner,
                imageFilesState = imageFilesState
            )
        }
    }
}