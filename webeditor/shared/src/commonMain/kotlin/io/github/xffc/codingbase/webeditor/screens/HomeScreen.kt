package io.github.xffc.codingbase.webeditor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen

@Composable
fun HomeScreenContent(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text("Web code editor", style = MaterialTheme.typography.headlineMediumEmphasized)

        Spacer(modifier = Modifier.height(16.dp))

        Button({ navController.navigate(ProjectScreen) }) {
            Text("New project")
        }
    }
}