package ru.kulishov.markweave.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily

object AppTypography {
    var typography by mutableStateOf(Typography())

    fun setNewFont(
        fontRegular: Font,
        fontMedium:Font,
        fontSemiMedium:Font,
        fontBold:Font,
        fontBlack: Font
    ){
        val newTypography = Typography(
            displayLarge =  typography.displayLarge.copy(fontFamily = fontBold.toFontFamily()),
            displayMedium = typography.displayMedium.copy(fontFamily = fontBold.toFontFamily()),
            displaySmall =  typography.displaySmall.copy(fontFamily = fontBold.toFontFamily()),
            headlineLarge = typography.headlineLarge.copy(fontFamily = fontBold.toFontFamily()),
            headlineMedium =  typography.headlineMedium.copy(fontFamily = fontBold.toFontFamily()),
            headlineSmall =   typography.headlineSmall.copy(fontFamily = fontBold.toFontFamily()),
            titleLarge = typography.titleLarge.copy(fontFamily = fontBold.toFontFamily()),
            titleMedium = typography.titleMedium.copy(fontFamily = fontBold.toFontFamily()),
            titleSmall =  typography.titleSmall.copy(fontFamily = fontBold.toFontFamily()),
            bodyLarge =  typography.bodyLarge.copy(fontFamily = fontBold.toFontFamily()),
            bodyMedium = typography.bodyMedium.copy(fontFamily = fontBold.toFontFamily()),
            bodySmall =  typography.bodySmall.copy(fontFamily = fontBold.toFontFamily()),
            labelLarge =  typography.labelLarge.copy(fontFamily = fontBold.toFontFamily()),
            labelMedium =  typography.labelMedium.copy(fontFamily = fontBold.toFontFamily()),
            labelSmall =  typography.labelSmall.copy(fontFamily = fontBold.toFontFamily()),
        )

        typography=newTypography
    }

    fun setTypography(
        displayLarge: TextStyle? = null,
        displayMedium: TextStyle? = null,
        displaySmall: TextStyle? = null,
        headlineLarge: TextStyle? = null,
        headlineMedium: TextStyle? = null,
        headlineSmall: TextStyle? = null,
        titleLarge: TextStyle? = null,
        titleMedium: TextStyle? = null,
        titleSmall: TextStyle? = null,
        bodyLarge: TextStyle? = null,
        bodyMedium: TextStyle? = null,
        bodySmall: TextStyle? = null,
        labelLarge: TextStyle? = null,
        labelMedium: TextStyle? = null,
        labelSmall: TextStyle? = null,
    ) {
        val newTypography = Typography(
            displayLarge = displayLarge ?: typography.displayLarge,
            displayMedium = displayMedium ?: typography.displayMedium,
            displaySmall = displaySmall ?: typography.displaySmall,
            headlineLarge = headlineLarge ?: typography.headlineLarge,
            headlineMedium = headlineMedium ?: typography.headlineMedium,
            headlineSmall = headlineSmall ?: typography.headlineSmall,
            titleLarge = titleLarge ?: typography.titleLarge,
            titleMedium = titleMedium ?: typography.titleMedium,
            titleSmall = titleSmall ?: typography.titleSmall,
            bodyLarge = bodyLarge ?: typography.bodyLarge,
            bodyMedium = bodyMedium ?: typography.bodyMedium,
            bodySmall = bodySmall ?: typography.bodySmall,
            labelLarge = labelLarge ?: typography.labelLarge,
            labelMedium = labelMedium ?: typography.labelMedium,
            labelSmall = labelSmall ?: typography.labelSmall,
        )

        typography=newTypography
    }
}