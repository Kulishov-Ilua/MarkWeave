package ru.kulishov.markweave.model

import androidx.compose.ui.text.AnnotatedString

data class Quote(
    val name: String? = null,
    val type: String = "info",
    var text: AnnotatedString
)