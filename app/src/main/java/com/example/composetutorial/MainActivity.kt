package com.example.composetutorial

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Navigation()
            }
        }
    }
}
data class Message(val author: String, val body: String)

@Preview
@Composable
fun ConversationPreview() {
    ComposeTutorialTheme {
        Navigation()
    }
}
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "conversation") {
        composable("conversation") { Conversation(SampleData.conversationSample, onNavigateToProfile = { navController.navigate("profile") { popUpTo ("profile") {inclusive = true} } }) }
        composable("profile") {
            Profile(
                onNavigateToConversation = {
                    navController.navigate("conversation")})
        }
    }
}