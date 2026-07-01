package io.github.xffc.codingbase.editor

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.xffc.codingbase.editor.screens.*

@Composable
fun App() {
    val navController = rememberNavController()

    var colorScheme by remember { mutableStateOf(darkColorScheme()) }

    MaterialTheme(colorScheme) {
        Surface(color = MaterialTheme.colorScheme.surfaceBright) {
            NavHost(navController, startDestination = HomeScreen) {
                composable<HomeScreen> { HomeScreenContent(navController) }
                composable<ProjectScreen> { ProjectScreenContent(navController) }
            }
        }
    }
}