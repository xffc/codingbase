package io.github.xffc.codingbase.editor

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Code editor",
    ) {
        App()
    }
}