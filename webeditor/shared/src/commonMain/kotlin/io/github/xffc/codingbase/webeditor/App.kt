package io.github.xffc.codingbase.webeditor

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEach
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.xffc.codingbase.webeditor.screen.*

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeScreen
        ) {
            composable<HomeScreen> { HomeScreenContent(navController) }
            composable<ProjectScreen> { ProjectScreenContent(navController) }
        }
    }
}