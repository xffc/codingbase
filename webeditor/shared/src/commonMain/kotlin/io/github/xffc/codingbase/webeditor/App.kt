package io.github.xffc.codingbase.webeditor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
        Surface(
            modifier = Modifier.fillMaxSize(),
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