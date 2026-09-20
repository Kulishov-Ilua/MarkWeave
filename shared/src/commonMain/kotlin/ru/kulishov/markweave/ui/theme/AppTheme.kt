package ru.kulishov.markweave.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme (content: @Composable ()-> Unit){
    val currentColorScheme = if(isSystemInDarkTheme()) AppColors.darkTheme else AppColors.lightTheme
    MaterialTheme(
        content=content,
        colorScheme = currentColorScheme
    )
}