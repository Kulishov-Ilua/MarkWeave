package ru.kulishov.markweave

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.kulishov.markweave.ui.MDReader
import ru.kulishov.markweave.ui.theme.AppTheme

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MarkWeave",
    ) {
        AppTheme {
        MDReader()
        }

    }
}