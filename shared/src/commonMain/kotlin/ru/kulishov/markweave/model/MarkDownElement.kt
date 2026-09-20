package ru.kulishov.markweave.model

import androidx.compose.runtime.Composable

data class MarkDownElement(
    val trigger:String,
    val groupID:Int,
    val checkFun:((String) -> Boolean)? = null,
    val viewData: MarkDownElementViewData,
    val transformer:(List<String>,(suspend (Long) -> CustomData?)?,((ByteFile) -> Unit)?) -> (@Composable ()->Unit),
)
