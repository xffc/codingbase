package io.github.xffc.codingbase.webeditor

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.xffc.codingbase.webeditor.screens.HomeScreen
import io.github.xffc.codingbase.webeditor.screens.HomeScreenContent
import io.github.xffc.codingbase.webeditor.screens.ProjectScreen
import io.github.xffc.codingbase.webeditor.screens.ProjectScreenContent

@Composable
fun App() {
    val navController = rememberNavController()

    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceBright
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
}