package com.example.composetutorial

import androidx.compose.runtime.Composable
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
                })
        }
    }
}