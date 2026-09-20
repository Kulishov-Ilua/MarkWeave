package ru.kulishov.markweave.model

import androidx.compose.runtime.Composable

data class MarkDownElementViewData(
    val name:String,
    val icon: @Composable () -> Unit,
    val onClick: ()-> String
)
