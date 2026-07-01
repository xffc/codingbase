package io.github.xffc.codingbase.editor.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.github.xffc.codingbase.editor.infinitecanvas.InfiniteCanvas
import kotlinx.serialization.Serializable

@Serializable
data object ProjectScreen

@Composable
fun ProjectScreenContent(navController: NavController) {
    Column(Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text("Untitled project")
            },
            navigationIcon = {
                IconButton({ navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Home screen")
                }
            }
        )

        InfiniteCanvas(Modifier.fillMaxSize())
    }
}