package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import android.content.res.Configuration
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.fillMaxSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                val navController = rememberNavController()
                val onNavigateToProfile: () -> Unit = {
                    navController.navigate("profile") }

                Conversation(SampleData.conversationSample, onNavigateToProfile)
                Surface(modifier = Modifier.fillMaxSize()) {
                    Greeting(Message("Android", "Jetpack Compose"))
                    }
                }
            }
        }
    }

data class Message(val author: String, val body: String)


@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun ConversationPreview() {
    ComposeTutorialTheme {
        Navigation()
            //Conversation(SampleData.conversationSample, onNavigateToProfile: () -> Unit)
    }
}
@Composable
fun Navigation() {
    // In a separate file or within a Composable function
    val navController = rememberNavController()
    NavHost(navController, startDestination = "conversation") {
        composable("conversation") { Conversation(SampleData.conversationSample, onNavigateToProfile = { navController.navigate("profile") { popUpTo ("profile") {inclusive = true} } }) }
        composable("profile") { Profile(onNavigateToConversation = {navController.navigate("conversation") }) }}
}