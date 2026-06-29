package io.github.xffc.codingbase.webeditor.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.github.xffc.codingbase.webeditor.InfiniteCanvas
import kotlinx.serialization.Serializable

@Serializable
data object ProjectScreen

@Composable
fun ProjectScreenContent(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar({
            Button({
                navController.navigate(HomeScreen)
            }) {
                Text("Close project")
            }
        })

        InfiniteCanvas()
    }
}