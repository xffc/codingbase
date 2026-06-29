package io.github.xffc.codingbase.webeditor.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data object ProjectScreen

@Composable
fun ProjectScreenContent(navController: NavController) {
    Button({
        navController.navigate(HomeScreen)
    }) {
        Text("Close project")
    }
}